package com.keiver.madara.common.request.mng;

import com.keiver.madara.common.request.BasePageRequest;
import com.keiver.madara.common.utils.RuntimeAssertUtil;

/**
 * web平台角色列表查询请求
 * 
 * @author prd-ckf
 * @version $Id: MngRoleQueryRequest.java, v 0.1 2018年4月3日 下午9:21:14 prd-ckf Exp $
 */
public class MngRoleQueryRequest extends BasePageRequest {

    /** uid */
    private static final long serialVersionUID = -494119455600633864L;

    @Override
    public void validate() {
        RuntimeAssertUtil.assertNotNull(pageNum, "pageNum不能为空");

        RuntimeAssertUtil.assertNotNull(pageSize, "pageSize不能为空");

    }

    /**
     * 角色编码
     * 表字段 : MNG_ROLE.ROLE_CODE
     */
    private String roleCode;

    /**
     * 角色名
     * 表字段 : MNG_ROLE.NAME
     */
    private String name;

    /**
     * 创建人用户编码
     * 表字段 : MNG_ROLE.CREATE_USER_CODE
     */
    private String createUserCode;

    /**
     * 修改人用户编码
     * 表字段 : MNG_ROLE.UPDATE_USER_CODE
     */
    private String updateUserCode;

    /**
     * 状态 NORMAL:生效 CLOSED:注销
     * 表字段 : MNG_ROLE.STATUS
     */
    private String status;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    public String getUpdateUserCode() {
        return updateUserCode;
    }

    public void setUpdateUserCode(String updateUserCode) {
        this.updateUserCode = updateUserCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
