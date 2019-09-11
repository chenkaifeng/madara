package com.keiver.madara.common.base;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 基础基类
 * @author prd-ckf
 * @version $Id: BaseDomain.java, v 0.1 2018年1月2日 下午3:58:00 prd-ckf Exp $
 */
public class BaseDomain implements Serializable {

    /** uid */
    private static final long serialVersionUID = 4433732778288878622L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
