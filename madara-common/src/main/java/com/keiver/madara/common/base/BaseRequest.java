package com.keiver.madara.common.base;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 基础请求类
 * @author prd-ckf
 * @version $Id: BaseRequest.java, v 0.1 2018年1月16日 下午4:59:55 prd-fuy Exp $
 */
public abstract class BaseRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 1584615303866040072L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * 参数校验
     */
    public abstract void validate();

}
