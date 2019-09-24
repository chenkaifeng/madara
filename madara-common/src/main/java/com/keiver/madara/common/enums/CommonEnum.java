package com.keiver.madara.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 一些基本枚举
 * <p>如TRUE/FALSE,NORMAL/ABNORMAL/CLOSED/FROZEN等</p>
 * @author prd-ckf
 * @version $Id: YesOrNoEnum.java, v 0.1 2018年3月19日 下午3:55:12 prd-ckf Exp $
 */
public enum CommonEnum {

    NORMAL("NORMAL", "正常"),

    ABNORMAL("ABNORMAL", "不正常"),

    CLOSED("CLOSED", "注销"),

    FROZEN("FROZEN", "冻结");

    /** 枚举代码 */
    private String code;

    /** 枚举值 */
    private String desc;

    private CommonEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
       
        * 根据代码获取枚举，如果code对应的枚举不存在，则返回null
       
        * @param code 枚举代码
       
        * @return     对应的枚举对象
       
        */
    public static CommonEnum getByCode(String code) {
        for (CommonEnum eachValue : CommonEnum.values()) {
            if (StringUtils.equals(code, eachValue.getCode())) {
                return eachValue;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
