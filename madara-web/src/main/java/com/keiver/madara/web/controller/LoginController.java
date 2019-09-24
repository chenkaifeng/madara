package com.keiver.madara.web.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.web.base.shiro.ShiroUser;
import com.keiver.madara.web.base.utils.ResultSet;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * 登陆
 * @author prd-ckf
 * @version $Id: LoginController.java, v 0.1 2018年1月11日 下午7:23:12 prd-ckf Exp $
 */
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Producer producer;

    /**
     * 获取验证码
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("static/images/captcha.jpg")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        //保存到session
        SecurityUtils.getSubject().getSession().setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 执行登录操作
     * <p>登录失败时返回99；登录成功时返回00</p>
     * @param username  用户名
     * @param password  密码
     * @param captcha   验证码
     * @return
     */
    @PostMapping("/login")
    public ResultSet login(String username, String password, String captcha) {
        if(StringUtils.isBlank(username)){
            return ResultSet.error("请输入用户名");
        }
        if(StringUtils.isBlank(password)){
            return ResultSet.error("请输入密码");
        }
        if(StringUtils.isBlank(captcha)){
            return ResultSet.error("请输入验证码");
        }
        LogUtil.info(logger, "接收到用户登录请求，用户名={0},验证码={1}", username, captcha);
        //1.校验验证码
        String trueCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        if (trueCaptcha == null || !trueCaptcha.equalsIgnoreCase(captcha)) {
            LogUtil.info(logger, "验证码{0}错误", captcha);
            return ResultSet.error("验证码错误");
        }

        //2.执行shiro认证方法
        ResultSet result = ResultSet.success();
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        } catch (UnknownAccountException e) {
            result = ResultSet.error("账号或密码不正确");
        } catch (IncorrectCredentialsException e) {
            result = ResultSet.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            result = ResultSet.error("账号已被锁定,请联系管理员");
        } catch (DisabledAccountException e) {
            result = ResultSet.error("账户状态非法");
        } catch (RuntimeException e) {
            result = ResultSet.error(e.getLocalizedMessage());
        }
        LogUtil.info(logger, "用户名={0},登录结果为:{1}", username, JSON.toJSONString(result));
        return result;
    }

    /**
     * 执行登出操作
     */
    @PostMapping("/logout")
    public ResultSet logout() {
        ShiroUser curUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUtil.info(logger, "接收到用户登出请求，用户名={0}", curUser.getLoginName());
        SecurityUtils.getSubject().logout();
        LogUtil.info(logger, "用户登出成功，用户名={0}", curUser.getLoginName());
        return ResultSet.success();
    }
}
