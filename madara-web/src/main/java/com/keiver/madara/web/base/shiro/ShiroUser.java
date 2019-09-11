package com.keiver.madara.web.base.shiro;

import com.keiver.madara.common.base.BaseDomain;

import java.util.Objects;
import java.util.Set;


/**
 * 自定义shiro中的Principal，使其除了登录名外还包括姓名，role集合
 * @author prd-ckf
 * @version $Id: ShiroUser.java, v 0.1 2018年1月21日 下午7:19:45 prd-ckf Exp $
 */
public class ShiroUser extends BaseDomain {

    /**  */
    private static final long serialVersionUID = 1L;

    private String            loginName;
    private Long              userId;
    private String            name;
    private Set<String>       roleSet;

    public ShiroUser(String loginName, Long userId, String name, Set<String> roleSet) {
        this.loginName = loginName;
        this.userId = userId;
        this.name = name;
        this.roleSet = roleSet;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<String> roleSet) {
        this.roleSet = roleSet;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 只输出loginName
     */
    @Override
    public String toString() {
        return loginName;
    }

    /**
     * 重载hashCode,只计算loginName;
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(loginName);
    }

    /**
     * 重载equals,只计算loginName;
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShiroUser other = (ShiroUser) obj;
        if (loginName == null) {
            if (other.loginName != null) {
                return false;
            }
        } else if (!loginName.equals(other.loginName)) {
            return false;
        }
        return true;
    }
}
