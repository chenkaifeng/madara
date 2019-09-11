package com.keiver.madara.common.domain;


import com.keiver.madara.common.base.BaseDomain;

public class MngUserRole extends BaseDomain {

    /** uid */
    private static final long serialVersionUID = 7626863999486513967L;

    /**
     * ID主键
     * 表字段 : MNG_USER_ROLE.ID
     */
    private Long              id;

    /**
     * 用户ID
     * 表字段 : MNG_USER_ROLE.USER_ID
     */
    private Long              userId;

    /**
     * 角色ID
     * 表字段 : MNG_USER_ROLE.ROLE_ID
     */
    private Long              roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}