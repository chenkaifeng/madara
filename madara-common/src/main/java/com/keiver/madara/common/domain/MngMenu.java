package com.keiver.madara.common.domain;

import com.keiver.madara.common.base.BaseDomain;

import java.util.List;


public class MngMenu extends BaseDomain {

    /** uid */
    private static final long serialVersionUID = -531447968521602421L;

    /**
     * ID主键
     * 表字段 : MNG_MENU.ID
     */
    private Long              id;

    /**
     * 菜单编码
     * 表字段 : MNG_MENU.MENU_CODE
     */
    private String            menuCode;

    /**
     * 父菜单编码
     * 表字段 : MNG_MENU.PARENT_CODE
     */
    private String            parentCode;

    /**
     * 菜单名称
     * 表字段 : MNG_MENU.NAME
     */
    private String            name;

    /**
     * 菜单URL
     * 表字段 : MNG_MENU.URL
     */
    private String            url;

    /**
     * 授权字符串
     * 表字段 : MNG_MENU.PERMISSION
     */
    private String            permission;

    /**
     * 类型CATALOG：目录   MENU：菜单   BUTTON：按钮
     * 表字段 : MNG_MENU.TYPE
     */
    private String            type;

    /**
     * 菜单图标名
     * 表字段 : MNG_MENU.ICON
     */
    private String            icon;

    /**
     * 排序号
     * 表字段 : MNG_MENU.ORDERING
     */
    private Long              ordering;

    /**
     * 父菜单名称
     */
    private String            parentName;

    /**
     * 用于存储子菜单列表
     */
    private List<?>           list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Long getOrdering() {
        return ordering;
    }

    public void setOrdering(Long ordering) {
        this.ordering = ordering;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}