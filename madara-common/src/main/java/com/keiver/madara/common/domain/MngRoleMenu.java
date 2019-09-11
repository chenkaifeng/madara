package com.keiver.madara.common.domain;


import com.keiver.madara.common.base.BaseDomain;

public class MngRoleMenu extends BaseDomain {

    /** uid */
    private static final long serialVersionUID = -5649668416592528345L;

    /**
     * ID主键
     * 表字段 : MNG_ROLE_MENU.ID
     */
    private Long              id;

    /**
     * 角色ID
     * 表字段 : MNG_ROLE_MENU.ROLE_ID
     */
    private Long              roleId;

    /**
     * 菜单ID
     * 表字段 : MNG_ROLE_MENU.MENU_ID
     */
    private Long              menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}