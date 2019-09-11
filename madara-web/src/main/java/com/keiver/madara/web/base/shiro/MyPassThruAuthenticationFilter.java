package com.keiver.madara.web.base.shiro;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.web.base.utils.ResultSet;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;

/**
 * 重写shiro过滤器，以满足前后端分离的场景
 * 
 * @author Chenkf
 * @version $Id: MyPassThruAuthenticationFilter.java, v 0.1 2019年1月30日 下午3:14:31 Chenkf Exp $
 */
public class MyPassThruAuthenticationFilter extends PassThruAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyPassThruAuthenticationFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            return true;
        } else {
            this.saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    /**
     * 原始的redirectToLogin方法会使得请求重定向到loginUrl，这里重写，区分ajax请求和页面请求
     * @see org.apache.shiro.web.filter.AccessControlFilter#redirectToLogin(ServletRequest, ServletResponse)
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        request = (HttpServletRequest) request;
        //如果是Ajax请求，状态码返回401
        if (WebUtils.toHttp(request).getHeader("x-requested-with") != null) {
            HttpServletResponse ajaxResponse = WebUtils.toHttp(response);
            ajaxResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            ajaxResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            ajaxResponse.setHeader("Error-Json", "ajaxError");
            try {
                ajaxResponse.getOutputStream().write(JSON.toJSONString(ResultSet.error("登录已过期")).getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                LogUtil.error(e1, logger, "[ExceptionHandler]全局异常处理器处理过程发生编码不支持异常");
            } catch (IOException e1) {
                LogUtil.error(e1, logger, "[ExceptionHandler]全局异常处理器处理处理过程发生IO异常");
            }
            return;
        }
        //非Ajax异常，跳转至登录页
        String loginUrl = getLoginUrl();
        WebUtils.issueRedirect(request, response, loginUrl);
    }

}
