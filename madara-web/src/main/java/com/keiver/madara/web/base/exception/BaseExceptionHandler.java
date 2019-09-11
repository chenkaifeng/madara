package com.keiver.madara.web.base.exception;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.web.base.utils.ResultSet;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.alibaba.fastjson.JSON;

/**
 * 全局异常处理器
 * @author prd-ckf
 * @version $Id: BaseExceptionHandler.java, v 0.1 2018年2月2日 下午3:24:17 prd-ckf Exp $
 */
@ControllerAdvice
public class BaseExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /**
     * 捕获Shiro对应异常并做处理
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    public ModelAndView handleAuthorizationException(HttpServletRequest request, HttpServletResponse response, AuthorizationException e) {
        //如果是Ajax异常，包装错误返回集以json形式返回
        if (request.getHeader("x-requested-with") != null) {
            LogUtil.error(e, logger, "[ExceptionHandler]ajax请求，Shiro权限校验不通过");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setStatus(300);
            response.setHeader("Error-Json", "ajaxError");
            try {
                response.getOutputStream().write(JSON.toJSONString(ResultSet.error("您没有此项权限！")).getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                LogUtil.error(e, logger, "[ExceptionHandler]全局异常处理器处理过程发生编码不支持异常");
            } catch (IOException e1) {
                LogUtil.error(e, logger, "[ExceptionHandler]全局异常处理器处理处理过程发生IO异常");
            }
            return new ModelAndView();
        }
        //非Ajax异常，跳转至未授权页面
        LogUtil.error(e, logger, "[ExceptionHandler]Shiro权限校验不通过");
        return new ModelAndView("redirect:/unauth");
    }

    /**
     * 捕获自定义的RuntimeException
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResultSet handleRuntimeException(RuntimeException e) {
        LogUtil.error(e, logger, "[ExceptionHandler]接收到运行时异常{0}", e);
        return ResultSet.error(e.getLocalizedMessage());
    }

    /**
     * 捕获未定义异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultSet handleException(Exception e) {
        LogUtil.error(e, logger, "[ExceptionHandler]接收到未知异常{0}", e);
        return ResultSet.error("未知异常");
    }
}
