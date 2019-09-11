package com.keiver.madara.common.request.mng;

import com.keiver.madara.common.request.BasePageRequest;
import com.keiver.madara.common.utils.RuntimeAssertUtil;

/**
 * web平台用户列表查询请求
 * 
 * @author prd-ckf
 * @version $Id: MngUserQueryRequest.java, v 0.1 2018年4月3日 下午8:53:22 prd-ckf Exp $
 */
public class MngUserQueryRequest extends BasePageRequest {

    /** uid */
    private static final long serialVersionUID = -1971057422449400246L;

    @Override
    public void validate() {
        RuntimeAssertUtil.assertNotNull(pageNum, "pageNum不能为空");

        RuntimeAssertUtil.assertNotNull(pageSize, "pageSize不能为空");

    }

    /**
     * 用户名
     * 表字段 : MNG_USER.NAME
     */
    private String name;

    /**
     * 状态 NORMAL:生效 CLOSED:注销  FROZEN:锁定
     * 表字段 : MNG_USER.STATUS
     */
    private String status;

    /**
     * 创建人
     * 表字段 : MNG_USER.CREATE_USER
     */
    private String createUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

}
