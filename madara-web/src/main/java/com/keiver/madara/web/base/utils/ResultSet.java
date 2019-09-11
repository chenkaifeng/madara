package com.keiver.madara.web.base.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回前端的结果集，00-成功，99-失败
 * @author prd-ckf
 * @version $Id: ResultSet.java, v 0.1 2018年2月1日 下午8:51:52 prd-ckf Exp $
 */
public class ResultSet extends HashMap<String, Object> {

    private static final long   serialVersionUID = 1L;

    public static final String  SUCCESS_CODE     = "00";
    public static final String  ERROR_CODE       = "99";

    public static final String  SUCCESS_MSG      = "success";
    public static final String  ERROR_MSG        = "未知异常";

    private static final String CODE             = "code";
    private static final String MSG              = "msg";

    public ResultSet() {
        put(CODE, SUCCESS_CODE);
        put(MSG, SUCCESS_MSG);
    }

    public static ResultSet success(String msg) {
        ResultSet r = new ResultSet();
        r.put(MSG, msg);
        return r;
    }

    public static ResultSet success(Map<String, Object> map) {
        ResultSet r = new ResultSet();
        r.putAll(map);
        return r;
    }

    public static ResultSet success() {
        return new ResultSet();
    }

    public static ResultSet error() {
        return error(ERROR_CODE, ERROR_MSG);
    }

    public static ResultSet error(String msg) {
        return error(ERROR_CODE, msg);
    }

    public static ResultSet error(String code, String msg) {
        ResultSet r = new ResultSet();
        r.put(CODE, code);
        r.put(MSG, msg);
        return r;
    }

    public ResultSet put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
