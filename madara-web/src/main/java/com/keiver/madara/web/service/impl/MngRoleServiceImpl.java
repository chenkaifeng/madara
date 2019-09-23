package com.keiver.madara.web.service.impl;

import com.github.pagehelper.PageInfo;
import com.keiver.madara.common.domain.MngRole;
import com.keiver.madara.common.domain.MngUser;
import com.keiver.madara.common.enums.CommonEnum;
import com.keiver.madara.common.request.mng.MngRoleQueryRequest;
import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.common.utils.RuntimeAssertUtil;
import com.keiver.madara.web.base.constant.WebConstant;
import com.keiver.madara.web.base.shiro.ShiroUser;
import com.keiver.madara.web.repository.MngRoleMenuRepository;
import com.keiver.madara.web.repository.MngRoleRepository;
import com.keiver.madara.web.repository.MngUserRepository;
import com.keiver.madara.web.service.MngRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 角色服务实现类
 *
 * @author prd-ckf
 * @version $Id: MngRoleServiceImpl.java, v 0.1 2018年4月3日 下午9:33:46 prd-ckf Exp $
 */
@Service
public class MngRoleServiceImpl implements MngRoleService {

    private static final Logger logger = LoggerFactory.getLogger(MngRoleServiceImpl.class);

    @Resource
    private MngRoleRepository mngRoleRepository;

    @Resource
    private MngUserRepository mngUserRepository;

    @Resource
    private MngRoleMenuRepository mngRoleMenuRepository;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public PageInfo<MngRole> queryListByPage(MngRoleQueryRequest request) {
        return mngRoleRepository.queryListByPage(request);
    }

    @Override
    public MngRole getRoleInfo(long roleId) {

        LogUtil.info(logger, "[Service]处理#获取角色信息#请求,roleId={0}", roleId);

        //1.根据角色ID获取角色信息
        MngRole role = mngRoleRepository.getRoleInfo(roleId);

        //2.根据角色ID获取权限列表信息
        List<Long> menuIdList = mngRoleMenuRepository.getMenuIdListByRoleId(roleId);

        //3.组装结果返回
        role.setMenuIdList(menuIdList);
        return role;
    }

    @Override
    public void updateRoleInfo(final MngRole mngRole) {

        LogUtil.info(logger, "[Service]处理#更新角色信息#请求,mngRole={0}", mngRole);

        //1.获取当前用户编码，作为修改人
        final String updateUserCode = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getLoginName();

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    //2.更新角色信息
                    mngRoleRepository.updateRole(mngRole, updateUserCode);

                    //3.删除该角色原有的角色与权限对应关系
                    mngRoleMenuRepository.deleteByRoleId(mngRole.getId());

                    //4.新增角色与权限对应关系
                    mngRoleMenuRepository.insertRoleMenu(mngRole.getId(), mngRole.getMenuIdList());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @Override
    public void addRoleInfo(final MngRole mngRole) {

        LogUtil.info(logger, "[Service]处理#新增角色信息#请求,mngRole={0}", mngRole);

        //1.获取当前用户编码，作为创建人
        final String createUserCode = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getLoginName();

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //2.新增角色信息，同时获得主键
                long roleId = mngRoleRepository.addRole(mngRole, createUserCode);

                //3.新增角色与权限对应关系
                mngRoleMenuRepository.insertRoleMenu(roleId, mngRole.getMenuIdList());
            }
        });

    }

    @Override
    public List<MngRole> queryList(MngRoleQueryRequest request) {

        LogUtil.info(logger, "[Service]处理#获取角色列表#请求,request={0}", request);

        return mngRoleRepository.queryList(request);
    }

    @Override
    public void closeRole(List<Long> roleIdList) {

        LogUtil.info(logger, "[Service]处理#批量注销角色#请求,roleIdList={0}", roleIdList);

        List<MngRole> roleList = mngRoleRepository.queryListByRoleIdList(roleIdList);

        for (MngRole role : roleList) {
            RuntimeAssertUtil.assertNotEquals(role.getRoleCode(), WebConstant.SUPER_ROLE_CODE, "管理员角色" + role.getRoleCode() + "不允许注销！");
            RuntimeAssertUtil.assertNotEquals(role.getStatus(), CommonEnum.CLOSED.getCode(), "角色" + role.getRoleCode() + "已注销！");
            List<MngUser> userList = mngUserRepository.queryListByRoleId(role.getId());
            if (CollectionUtils.isNotEmpty(userList)) {
                for (MngUser user : userList) {
                    RuntimeAssertUtil.assertEquals(user.getStatus(), CommonEnum.CLOSED.getCode(), "存在非注销用户" + user.getUserCode() + "关联该角色，不允许注销！");
                }
            }
        }

        //获取当前用户编码，作为修改人
        String updateUserCode = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getLoginName();

        mngRoleRepository.updateRoleStatus(roleIdList, CommonEnum.CLOSED.getCode(), new Date(), updateUserCode);

        LogUtil.info(logger, "[Service]处理#批量注销角色#请求成功");

    }

}
