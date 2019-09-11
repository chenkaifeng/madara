package com.keiver.madara.common.domain;

import java.util.Date;
import java.util.List;

import com.keiver.madara.common.base.BaseDomain;
import com.keiver.madara.common.utils.RuntimeAssertUtil;
import org.apache.commons.lang3.StringUtils;


public class MngRole extends BaseDomain {

    /** uid */
    private static final long serialVersionUID = 9207778022785999689L;

    /**
     * ID主键
     * 表字段 : MNG_ROLE.ID
     */
    private Long              id;

    /**
     * 角色编码
     * 表字段 : MNG_ROLE.ROLE_CODE
     */
    private String            roleCode;

    /**
     * 角色名
     * 表字段 : MNG_ROLE.NAME
     */
    private String            name;

    /**
     * 创建人用户编码
     * 表字段 : MNG_ROLE.CREATE_USER_CODE
     */
    private String            createUserCode;

    /**
     * 修改人用户编码
     * 表字段 : MNG_ROLE.UPDATE_USER_CODE
     */
    private String            updateUserCode;

    /**
     * 状态 NORMAL:生效 CLOSED:注销
     * 表字段 : MNG_ROLE.STATUS
     */
    private String            status;

    /**
     * 角色类型
     * 表字段 : MNG_ROLE.ROLE_TYPE
     */
    private String            roleType;

    /**
     * 创建时间
     * 表字段 : MNG_ROLE.GMT_CREATE
     */
    private Date              gmtCreate;

    /**
     * 修改时间
     * 表字段 : MNG_ROLE.GMT_UPDATE
     */
    private Date              gmtUpdate;

    /**
     * 备注
     * 表字段 : MNG_ROLE.REMARK
     */
    private String            remark;

    /**
     * 所包含的菜单ID列表
     */
    private List<Long>        menuIdList;

    /**
     * 校验
     */
    public void validate() {
        RuntimeAssertUtil.assertNotBlank(roleCode, "角色编码不能为空");
        RuntimeAssertUtil.assertNotBlank(name, "角色名不能为空");
        RuntimeAssertUtil.assertNotNull(menuIdList, "权限不能为空");

        RuntimeAssertUtil.assertTrue(StringUtils.length(roleCode) <= 16, "角色编码不能超过16个字符");
        RuntimeAssertUtil.assertTrue(StringUtils.length(name) <= 32, "角色名不能超过32个字符");
        RuntimeAssertUtil.assertTrue(StringUtils.length(remark) <= 64, "备注不能超过64个字符");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode == null ? null : createUserCode.trim();
    }

    public String getUpdateUserCode() {
        return updateUserCode;
    }

    public void setUpdateUserCode(String updateUserCode) {
        this.updateUserCode = updateUserCode == null ? null : updateUserCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType == null ? null : roleType.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }

}