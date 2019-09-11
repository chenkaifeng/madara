package com.keiver.madara.web.base.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 用户状态冻结异常
 * @author prd-ckf
 * @version $Id: UserStatusFrozenException.java, v 0.1 2018年4月11日 下午3:56:53 prd-ckf Exp $
 */
public class UserStatusFrozenException extends AuthenticationException {

    /** uid */
    private static final long serialVersionUID = 2777200676843669594L;

    public UserStatusFrozenException() {
        super();
    }

    public UserStatusFrozenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserStatusFrozenException(String message) {
        super(message);
    }

    public UserStatusFrozenException(Throwable cause) {
        super(cause);
    }
}