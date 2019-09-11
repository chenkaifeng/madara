package com.keiver.madara.web.controller;

import java.util.List;

import javax.annotation.Resource;

import com.keiver.madara.common.domain.MngRole;
import com.keiver.madara.common.enums.CommonEnum;
import com.keiver.madara.common.request.mng.MngRoleQueryRequest;
import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.web.base.utils.ResultSet;
import com.keiver.madara.web.service.MngRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.github.pagehelper.PageInfo;

/**
 * 角色控制器
 * @author prd-ckf
 * @version $Id: MngRoleController.java, v 0.1 2018年4月8日 上午9:31:46 prd-ckf Exp $
 */
@Controller
@RequestMapping("/sys/role")
public class MngRoleController {

    private static final Logger logger = LoggerFactory.getLogger(MngRoleController.class);

    @Resource
    private MngRoleService mngRoleService;

    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "sys/role";
    }

    /**
     * 分页获取角色列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultSet list(MngRoleQueryRequest request) {

        LogUtil.info(logger, "[Controller]收到#Web分页获取角色列表#请求，request={0}", request);

        PageInfo<MngRole> list = mngRoleService.queryListByPage(request);

        LogUtil.info(logger, "[Controller]处理#Web分页获取角色列表#结果，返回={0}", list);

        return ResultSet.success().put("page", list);
    }

    /**
     * 新增角色
     * @param mngRole
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet add(@RequestBody MngRole mngRole) {

        LogUtil.info(logger, "[Controller]收到#Web新增角色#请求，mngRole={0}", mngRole);

        mngRole.validate();

        mngRoleService.addRoleInfo(mngRole);

        LogUtil.info(logger, "[Controller]处理#Web新增角色#成功");

        return ResultSet.success();
    }

    /**
     * 更改角色信息
     * @param mngRole
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet update(@RequestBody MngRole mngRole) {

        LogUtil.info(logger, "[Controller]收到#Web修改角色信息#请求，mngRole={0}", mngRole);

        mngRole.validate();

        mngRoleService.updateRoleInfo(mngRole);

        LogUtil.info(logger, "[Controller]处理#Web修改角色信息#成功");

        return ResultSet.success();
    }

    /**
     * 获取单个角色信息(包括角色基本信息以及包含的权限信息)
     * @param request
     * @return
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public ResultSet info(long roleId) {
        LogUtil.info(logger, "[Controller]收到#Web获取单个角色信息#请求，roleId={0}", roleId);

        MngRole role = mngRoleService.getRoleInfo(roleId);

        return ResultSet.success().put("role", role);
    }

    /**
     * 获取角色选择列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/select")
    @ResponseBody
    public ResultSet select() {
        LogUtil.info(logger, "[Controller]收到#Web获取角色选择列表#请求");

        MngRoleQueryRequest request = new MngRoleQueryRequest();
        request.setStatus(CommonEnum.NORMAL.getCode());
        List<MngRole> roleList = mngRoleService.queryList(request);

        LogUtil.info(logger, "[Controller]处理#Web获取角色选择列表#结果，返回={0}", roleList);

        return ResultSet.success().put("roleList", roleList);
    }

    /**
     * 批量注销角色
     * @param roleIdList
     * @return
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet close(@RequestBody List<Long> roleIdList) {

        LogUtil.info(logger, "[Controller]收到#Web批量注销角色#请求，roleIdList={0}", roleIdList);

        mngRoleService.closeRole(roleIdList);

        LogUtil.info(logger, "[Controller]处理#Web批量注销角色#成功");

        return ResultSet.success();
    }
}
