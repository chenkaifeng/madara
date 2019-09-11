package com.keiver.madara.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.keiver.madara.common.domain.MngRole;
import com.keiver.madara.common.domain.MngUser;
import com.keiver.madara.common.enums.CommonEnum;
import com.keiver.madara.common.request.mng.MngUserQueryRequest;
import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.common.utils.RuntimeAssertUtil;
import com.keiver.madara.web.base.constant.WebConstant;
import com.keiver.madara.web.base.shiro.ShiroRealm;
import com.keiver.madara.web.base.shiro.ShiroUser;
import com.keiver.madara.web.repository.MngRoleRepository;
import com.keiver.madara.web.repository.MngUserRepository;
import com.keiver.madara.web.repository.MngUserRoleRepository;
import com.keiver.madara.web.service.MngUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.pagehelper.PageInfo;


/**
 * 管理员用户管理服务实现类
 * 
 * @author 51
 * @version $Id: MngUserServiceImpl.java, v 0.1 2018年4月4日 上午10:56:46 51 Exp $
 */
@Service
public class MngUserServiceImpl implements MngUserService {

    private static final Logger        logger = LoggerFactory.getLogger(MngUserServiceImpl.class);

    @Resource
    private MngUserRepository mngUserRepository;

    @Resource
    private MngRoleRepository mngRoleRepository;

    @Resource
    private MngUserRoleRepository mngUserRoleRepository;

    @Resource
    private TransactionTemplate        transactionTemplate;


    @Resource
    private ShiroRealm shiroRealm;

    @Override
    public MngUser getUserByID(Long userId) {

        LogUtil.info(logger, "[Service]处理#获取用户信息#请求,userId={0}", userId);

        //1.根据用户ID获取用户实例
        MngUser mngUser = mngUserRepository.getUserByID(userId);

        //2.根据用户ID获取角色ID列表
        List<Long> roleIdList = mngUserRoleRepository.getRoleIdListByUserId(userId);
        mngUser.setRoleIdList(roleIdList);

        List<String> roleName = new ArrayList<>();
        for (long roleId : roleIdList) {
            MngRole role = mngRoleRepository.getRoleInfo(roleId);
            roleName.add(role.getName());
        }
        if (roleName.size() > 0) {
            mngUser.setRoleName(roleName);
        }

        LogUtil.info(logger, "[Service]处理#获取用户信息#请求完成,mngUser={0}", mngUser);
        return mngUser;
    }

    @Override
    public MngUser getUserByUserCode(String userCode) {
        return mngUserRepository.getUserByUserCode(userCode);
    }

    @Override
    public Set<String> getPermsSetByUserId(Long userId) {
        return mngUserRepository.getPermsSetByUserId(userId);
    }

    @Override
    public PageInfo<MngUser> queryList(MngUserQueryRequest request) {
        return mngUserRepository.queryList(request);
    }

    @Override
    public void updateUserInfo(final MngUser mngUser) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                    //1.更新用户信息
                    mngUserRepository.updateMngUser(mngUser);

                    //2.删除该用户原有的用户与角色对应关系
                    mngUserRoleRepository.deleteByUserId(mngUser.getId());

                    //3.新增用户与角色对应关系
                    mngUserRoleRepository.insertUserRole(mngUser.getId(), mngUser.getRoleIdList());
            }
        });

    }

    @Override
    public void addUserInfo(final MngUser mngUser) {

        //1.判断用户编码是否已经注册
        String userCode = mngUser.getUserCode();
        MngUser tempUser = mngUserRepository.getUserByUserCode(userCode);
        RuntimeAssertUtil.assertNull(tempUser, "用户已注册！");

        //2.获取当前用户编码，作为创建人
        final String createUserCode = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getLoginName();

        //3.获取默认密码并生成密码MD5摘要 TODO
        String defaultPassword = "123456";
        final String password = hashPassword(defaultPassword, mngUser.getUserCode());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                    //4.新增用户，同时获得主键
                    long userId = mngUserRepository.saveMngUser(mngUser, createUserCode, password);

                    //5.新增用户与角色对应关系
                    mngUserRoleRepository.insertUserRole(userId, mngUser.getRoleIdList());
            }
        });
    }

    @Override
    public String updatePasswordWrongTimes(String userCode) {
        MngUser mngUser = mngUserRepository.getUserByUserCode(userCode);
        //TODO 最大错误次数从系统参数表取
        String maxWrongTimes = "5";
        short maxPasswordWrongTime = Short.parseShort(maxWrongTimes);
        short passwordWrongTime = mngUser.getLoginFailNum() == null ? 0 : mngUser.getLoginFailNum();
        passwordWrongTime += 1;
        if (passwordWrongTime < maxPasswordWrongTime) {
            mngUser.setLoginFailNum(Short.valueOf(passwordWrongTime));
            mngUserRepository.updateMngUser(mngUser);
            return "密码错误，您还有" + (maxPasswordWrongTime - passwordWrongTime) + "次重试机会";
        }
        mngUser.setLoginFailNum(maxPasswordWrongTime);
        mngUser.setStatus(CommonEnum.FROZEN.getCode());
        mngUserRepository.updateMngUser(mngUser);
        return "用户已被冻结，请联系管理员解锁";
    }

    @Override
    public MngUser getUserByCodeAndPassword(String userCode, String password) {
        return mngUserRepository.getUserByCodeAndPassword(userCode, password);
    }

    @Override
    public void closeUser(List<Long> userIdList) {
        LogUtil.info(logger, "[Service]处理#批量注销用户#请求,userIdList={0}", userIdList);

        List<MngUser> userList = mngUserRepository.queryListByUserIdList(userIdList);

        for (MngUser user : userList) {
            RuntimeAssertUtil.assertNotEquals(user.getUserCode(), ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getLoginName(), "不能注销用户自身！");
            RuntimeAssertUtil.assertNotEquals(user.getStatus(), CommonEnum.CLOSED.getCode(), "用户" + user.getUserCode() + "已注销！");
            RuntimeAssertUtil.assertNotEquals(user.getUserCode(), WebConstant.SUPER_USER_CODE, "管理员用户" + WebConstant.SUPER_USER_CODE + "不允许注销！");
        }

        mngUserRepository.updateUserStatus(userIdList, CommonEnum.CLOSED.getCode(), new Date(), false);

        LogUtil.info(logger, "[Service]处理#批量注销用户#请求成功");
    }

    @Override
    public void unfreezeUser(List<Long> userIdList) {
        LogUtil.info(logger, "[Service]处理#批量解冻用户#请求,userIdList={0}", userIdList);

        List<MngUser> userList = mngUserRepository.queryListByUserIdList(userIdList);

        for (MngUser user : userList) {
            RuntimeAssertUtil.assertEquals(user.getStatus(), CommonEnum.FROZEN.getCode(), "用户" + user.getUserCode() + "不是冻结状态！");
        }

        mngUserRepository.updateUserStatus(userIdList, CommonEnum.NORMAL.getCode(), new Date(), true);

        LogUtil.info(logger, "[Service]处理#批量解冻用户#请求成功");
    }

    @Override
    public void resetPassword(long userId) {
        LogUtil.info(logger, "[Service]处理#重置用户密码#请求,userId={0}", userId);

        MngUser mngUser = mngUserRepository.getUserByID(userId);

        RuntimeAssertUtil.assertEquals(mngUser.getStatus(), CommonEnum.NORMAL.getCode(), "用户" + mngUser.getUserCode() + "不是正常状态！");

        //TODO
        String defaultPassword = "123456";
        String password = hashPassword(defaultPassword, mngUser.getUserCode());
        mngUser.setPassword(password);
        mngUserRepository.updateMngUser(mngUser);

        LogUtil.info(logger, "[Service]处理#重置用户密码#请求成功");
    }

    /**
     * MD5生成密码摘要
     * @param credentials   密码明文
     * @param salt  盐
     * @return
     */
    private String hashPassword(String credentials, String salt) {
        //获取默认密码并生成密码MD5摘要
        HashedCredentialsMatcher hashedCredentialsMatcher = (HashedCredentialsMatcher) shiroRealm.getCredentialsMatcher();
        String hashAlgorithmName = hashedCredentialsMatcher.getHashAlgorithmName(); //加密算法
        int hashIterations = hashedCredentialsMatcher.getHashIterations(); //加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
        Object obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
        return obj.toString();
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        LogUtil.info(logger, "[Service]处理#修改用户密码#请求，oldPassword={0},newPassword={1}", oldPassword, newPassword);
        String curUserCode = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getLoginName();
        String oldPasswordHash = hashPassword(oldPassword, curUserCode);
        MngUser user = mngUserRepository.getUserByCodeAndPassword(curUserCode, oldPasswordHash);
        if (user == null) {
            throw new RuntimeException("旧密码错误");
        }

        String newPasswordHash = hashPassword(newPassword, curUserCode);
        user.setPassword(newPasswordHash);
        user.setChangePwdTime(new Date());
        mngUserRepository.updateMngUser(user);

        LogUtil.info(logger, "[Service]处理#修改用户密码#请求成功");
    }
}
