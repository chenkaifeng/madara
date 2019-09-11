package com.keiver.madara.web.controller;

import javax.annotation.Resource;

import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.web.base.utils.ResultSet;
import com.keiver.madara.web.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 授权控制器
 * @author Chenkf
 * @version $Id: AuthController.java, v 0.1 2018年4月10日 下午3:06:28 Chenkf Exp $
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private AuthService authService;

    /**
     * 临时授权
     * @param userCode
     * @param password
     * @param permission
     * @return
     */
    @RequestMapping(value = "/tempAuthorize", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet tempAuthorize(String userCode, String password, String permission) {

        LogUtil.info(logger, "[Controller]收到#Web临时授权#请求, userCode={0}, password={1}, permission={2}", userCode, password, permission);

        authService.tempAuthorize(userCode, password, permission);

        LogUtil.info(logger, "[Controller]处理#Web临时授权#成功");

        return ResultSet.success();

    }

}
