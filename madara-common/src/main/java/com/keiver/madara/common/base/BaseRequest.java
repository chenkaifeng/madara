package com.keiver.madara.common.base;

/**
 * 基础请求类
 * @author prd-ckf
 * @version $Id: BaseRequest.java, v 0.1 2018年1月16日 下午4:59:55 prd-fuy Exp $
 */
public abstract class BaseRequest extends BaseDomain {

    /** uid */
    private static final long serialVersionUID = 1584615303866040072L;

    /**
     * 参数校验
     */
    public abstract void validate();

}
