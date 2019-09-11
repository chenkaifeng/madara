package com.keiver.madara.web.service;

/**
 * 授权服务
 * @author Chenkf
 * @version $Id: AuthService.java, v 0.1 2018年4月13日 下午5:06:42 Chenkf Exp $
 */
public interface AuthService {

    /**
     * 临时授权
     * @param userCode
     * @param password
     * @param permission
     */
    void tempAuthorize(String userCode, String password, String permission);

}
