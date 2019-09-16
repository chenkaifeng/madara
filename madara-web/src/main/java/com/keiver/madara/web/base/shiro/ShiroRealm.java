package com.keiver.madara.web.base.shiro;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.keiver.madara.common.domain.MngUser;
import com.keiver.madara.common.enums.CommonEnum;
import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.web.repository.MngUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 自定义realm实现认证和授权
 * @author prd-ckf
 * @version $Id: ShiroRealm.java, v 0.1 2018年1月4日 下午2:25:36 prd-ckf Exp $
 */
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Resource
    private MngUserRepository mngUserRepository;

    @Override
    public void setName(String name) {
        super.setName("ShiroRealm");
    }

    /**
     * 认证
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        //1.根据token传入的username,从数据库获取user
        MngUser user = null;
        try {
            user = mngUserRepository.getUserByUserCode(token.getUsername());
        } catch (Exception e) {
            LogUtil.error(e, logger, "登陆时根据用户名查询数据库发生异常");
        }

        if (user != null) {

            //2.校验用户状态
            doUserStatusValidate(user.getStatus());

            //3. TODO 读取用户的角色列表
            Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();//roleService.selectResourceMapByUserId(user.getId());
            Set<String> roles = resourceMap.get("roles");
            ShiroUser shiroUser = new ShiroUser(user.getUserCode(), user.getId(), user.getName(), roles);

            //4.这里以username作为盐
            return new SimpleAuthenticationInfo(shiroUser, user.getPassword().trim(), ByteSource.Util.bytes(token.getUsername()), getName());
        }
        return null;
    }

    /**
     * 授权
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

        //获取用户权限字符串集合
        Set<String> permsSet = mngUserRepository.getPermsSetByUserId(shiroUser.getUserId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //细粒度-以权限资源为授权单位
        info.addStringPermissions(permsSet);
        //粗粒度-以角色为授权单位
        if (shiroUser.getRoleSet() != null) {
            info.addRoles(shiroUser.getRoleSet());
        }
        return info;
    }

    /**
     * 用户状态校验
     * @param status
     */
    private void doUserStatusValidate(String status) {
        if(!StringUtils.equals(status, CommonEnum.NORMAL.getCode())){
            throw new RuntimeException("用户状态异常，请联系管理员");
        }
    }

    @Override
    protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) super.getAvailablePrincipal(principals);
        return shiroUser.toString();
    }

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) super.getAvailablePrincipal(principals);
        return shiroUser.toString();
    }

    /**
     * 清除用户缓存
     * @param shiroUser
     */
    public void removeUserCache(ShiroUser shiroUser) {
        removeUserCache(shiroUser.getLoginName());
    }

    /**
     * 清除用户缓存
     * @param loginName
     */
    public void removeUserCache(String loginName) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(loginName, super.getName());
        super.clearCachedAuthenticationInfo(principals);
    }
}
