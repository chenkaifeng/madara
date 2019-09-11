package com.keiver.madara.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 页面对应控制器
 *
 * @author Chenkf
 * @version $Id: PageController.java, v 0.1 2019年1月14日 下午4:39:09 Chenkf Exp $
 */
@Controller
public class PageController {

    @GetMapping("login")
    public String login(HttpServletRequest request) {
        return "login";
    }

    @GetMapping(value = {"/", "index"})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"dashboard"})
    public String dashboard() {
        return "dashboard";
    }
}
