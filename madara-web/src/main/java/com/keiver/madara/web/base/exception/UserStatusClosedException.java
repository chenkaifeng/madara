package com.keiver.madara.web.base.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 用户状态注销异常
 * @author prd-ckf
 * @version $Id: UserStatusFrozenException.java, v 0.1 2018年4月11日 下午3:56:53 prd-ckf Exp $
 */
public class UserStatusClosedException extends AuthenticationException {

    /** uid */
    private static final long serialVersionUID = 5230170033393247173L;

    public UserStatusClosedException() {
        super();
    }

    public UserStatusClosedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserStatusClosedException(String message) {
        super(message);
    }

    public UserStatusClosedException(Throwable cause) {
        super(cause);
    }
}