package com.keiver.madara.web.base.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码异常类
 * @author prd-ckf
 * @version $Id: CaptchaException.java, v 0.1 2018年1月14日 下午7:02:21 prd-ckf Exp $
 */
public class CaptchaException extends AuthenticationException {

    /** uid */
    private static final long serialVersionUID = 5138001735303226746L;

    public CaptchaException() {
        super();
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }
}