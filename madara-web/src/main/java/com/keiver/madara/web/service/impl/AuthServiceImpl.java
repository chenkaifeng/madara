package com.keiver.madara.web.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import com.keiver.madara.common.domain.MngUser;
import com.keiver.madara.common.utils.RuntimeAssertUtil;
import com.keiver.madara.web.base.shiro.ShiroUser;
import com.keiver.madara.web.base.shiro.ShiroRealm;
import com.keiver.madara.web.repository.MngRoleRepository;
import com.keiver.madara.web.service.AuthService;
import com.keiver.madara.web.service.MngUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 临时授权服务实现类
 * @author Chenkf
 * @version $Id: AuthServiceImpl.java, v 0.1 2018年4月13日 下午5:08:35 Chenkf Exp $
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Resource
    private MngUserService mngUserService;

    @Resource
    private MngRoleRepository mngRoleRepository;

    @Resource
    private ShiroRealm shiroRealm;

    @Override
    public void tempAuthorize(String userCode, String password, String permission) {

        //1.基础信息校验
        commonValidate(userCode, password, permission);

        //2.当前用户信息校验
        String curUserCode = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getLoginName();
        curUserValidate(curUserCode, userCode);

        //3.授权用户信息校验
        MngUser manager = mngUserService.getUserByCodeAndPassword(userCode, getPasswordInHash(userCode, password));
        managerValidate(manager);

        //4.权限校验
        permissionValidate(manager, permission);

    }

    /**
     * 权限校验
     * @param manager
     * @param permission
     */
    private void permissionValidate(MngUser manager, String permission) {
        Set<String> permsSet = mngUserService.getPermsSetByUserId(manager.getId());
        RuntimeAssertUtil.assertNotBlank(permsSet, "授权用户无任何权限！");
        RuntimeAssertUtil.assertTrue(permsSet.contains(permission), "授权用户无此项授权权限！");
    }

    /**
     * 授权用户信息校验
     * @param manager
     */
    private void managerValidate(MngUser manager) {
        RuntimeAssertUtil.assertTrue(((null != manager) && StringUtils.isNotBlank(manager.getUserCode())), "授权用户用户名或密码不正确！");
    }

    /**
     * 当前用户信息校验
     * @param curUserCode
     * @param userCode
     */
    private void curUserValidate(String curUserCode, String userCode) {
        RuntimeAssertUtil.assertNotBlank(curUserCode, "当前用户为空!");
        RuntimeAssertUtil.assertNotEquals(curUserCode, userCode, "用户不能自身授权!");
    }

    /**
     * 基础信息校验
     * @param userCode
     * @param password
     * @param permission
     */
    private void commonValidate(String userCode, String password, String permission) {
        RuntimeAssertUtil.assertNotBlank(userCode, "用户名不能为空!");
        RuntimeAssertUtil.assertNotBlank(password, "密码不能为空!");
        RuntimeAssertUtil.assertNotBlank(permission, "权限不能为空!");
    }

    /**
     * 获取加密后密码
     * @param userCode
     * @param password
     * @return
     */
    private String getPasswordInHash(String userCode, String password) {
        HashedCredentialsMatcher hashedCredentialsMatcher = (HashedCredentialsMatcher) shiroRealm.getCredentialsMatcher();
        String hashAlgorithmName = hashedCredentialsMatcher.getHashAlgorithmName(); //加密算法
        int hashIterations = hashedCredentialsMatcher.getHashIterations(); //加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(userCode); //以UserCode作为salt
        Object obj = new SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations);
        return obj.toString();
    }

}
