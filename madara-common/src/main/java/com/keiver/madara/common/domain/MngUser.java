package com.keiver.madara.common.domain;

import java.util.Date;
import java.util.List;

import com.keiver.madara.common.base.BaseDomain;
import com.keiver.madara.common.utils.RuntimeAssertUtil;
import org.apache.commons.lang3.StringUtils;


public class MngUser extends BaseDomain {

    /** uid */
    private static final long serialVersionUID = -6681960411755573719L;

    /**
     * ID主键
     * 表字段 : MNG_USER.ID
     */
    private Long              id;

    /**
     * 用户编码
     * 表字段 : MNG_USER.USER_CODE
     */
    private String            userCode;

    /**
     * 用户名
     * 表字段 : MNG_USER.NAME
     */
    private String            name;

    /**
     * 用户密码摘要
     * 表字段 : MNG_USER.PASSWORD
     */
    private String            password;

    /**
     * 状态 NORMAL:生效 CLOSED:注销 FROZEN:锁定
     * 表字段 : MNG_USER.STATUS
     */
    private String            status;

    /**
     * 最后登录时间
     * 表字段 : MNG_USER.LAST_LOGIN_TIME
     */
    private Date              lastLoginTime;

    /**
     * 连续登录失败次数
     * 表字段 : MNG_USER.LOGIN_FAIL_NUM
     */
    private Short             loginFailNum;

    /**
     * 创建人编码
     * 表字段 : MNG_USER.CREATE_USER_CODE
     */
    private String            createUserCode;

    /**
     * 上次修改密码时间
     * 表字段 : MNG_USER.CHANGE_PWD_TIME
     */
    private Date              changePwdTime;

    /**
     * 创建时间
     * 表字段 : MNG_USER.GMT_CREATE
     */
    private Date              gmtCreate;

    /**
     * 修改时间
     * 表字段 : MNG_USER.GMT_UPDATE
     */
    private Date              gmtUpdate;

    /**
     * 备注
     * 表字段 : MNG_USER.REMARK
     */
    private String            remark;

    /**
     * 用户包含的角色ID列表
     */
    private List<Long>        roleIdList;

    /**
     * 用户角色名
     */
    private List<String>      roleName;

    /**
     * 校验
     */
    public void validate() {
        RuntimeAssertUtil.assertNotBlank(userCode, "用户编码不能为空");
        RuntimeAssertUtil.assertNotBlank(name, "用户名不能为空");
        RuntimeAssertUtil.assertNotNull(roleIdList, "角色不能为空");

        RuntimeAssertUtil.assertTrue(StringUtils.length(userCode) <= 16, "用户编码不能超过16个字符");
        RuntimeAssertUtil.assertTrue(StringUtils.length(name) <= 32, "用户名不能超过32个字符");
        RuntimeAssertUtil.assertTrue(StringUtils.length(remark) <= 64, "备注不能超过64个字符");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Short getLoginFailNum() {
        return loginFailNum;
    }

    public void setLoginFailNum(Short loginFailNum) {
        this.loginFailNum = loginFailNum;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode == null ? null : createUserCode.trim();
    }

    public Date getChangePwdTime() {
        return changePwdTime;
    }

    public void setChangePwdTime(Date changePwdTime) {
        this.changePwdTime = changePwdTime;
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

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(List<String> roleName) {
        this.roleName = roleName;
    }

}