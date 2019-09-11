package com.keiver.madara.web.controller;

import java.util.List;

import javax.annotation.Resource;

import com.keiver.madara.common.domain.MngMenu;
import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.web.base.shiro.ShiroUser;
import com.keiver.madara.web.base.utils.ResultSet;
import com.keiver.madara.web.service.MngMenuService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 菜单资源控制器
 * @author prd-ckf
 * @version $Id: MngMenuController.java, v 0.1 2018年4月8日 上午9:31:32 prd-ckf Exp $
 */
@RestController
@RequestMapping("/sys/menu")
public class MngMenuController {

    private static final Logger logger = LoggerFactory.getLogger(MngMenuController.class);

    @Resource
    private MngMenuService mngMenuService;

    /**
     * 获取导航栏菜单
     * @return
     */
    @RequestMapping(value = "/navigate", method = RequestMethod.GET)
    public ResultSet navigate() {

        LogUtil.info(logger, "[Controller]收到#Web获取导航栏菜单#请求");

        //1.获取当前用户ID
        Long userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();

        //2.从service获取导航栏菜单列表
        List<MngMenu> list = mngMenuService.getMenuListByUserId(userId);

        LogUtil.info(logger, "[Controller]处理#Web获取导航栏菜单#结果，返回={0}", list);

        //3.组装列表成功返回
        return ResultSet.success().put("menuList", list);
    }

    /**
     * 获取所有菜单资源
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<MngMenu> list() {

        LogUtil.info(logger, "[Controller]收到#Web获取所有菜单资源#请求");

        List<MngMenu> list = mngMenuService.getMenuList();

        LogUtil.info(logger, "[Controller]处理#Web获取所有菜单资源#结果，返回={0}", list);

        return list;
    }

    /**
     * 获取某角色拥有的菜单资源
     * @return
     */
    @RequestMapping(value = "/listByRole", method = RequestMethod.GET)
    public List<MngMenu> listByRole(long roleId) {

        LogUtil.info(logger, "[Controller]收到#Web获取某角色拥有的菜单资源#请求，roleId={0}", roleId);

        List<MngMenu> list = mngMenuService.getMenuListByRoleId(roleId);

        LogUtil.info(logger, "[Controller]处理#Web获取某角色拥有的菜单资源#结果，返回={0}", list);

        return list;
    }
}
