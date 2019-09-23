package com.keiver.madara.web.controller;

import com.github.pagehelper.PageInfo;
import com.keiver.madara.common.domain.MngUser;
import com.keiver.madara.common.request.mng.MngUserQueryRequest;
import com.keiver.madara.common.utils.LogUtil;
import com.keiver.madara.common.utils.RuntimeAssertUtil;
import com.keiver.madara.web.base.shiro.ShiroUser;
import com.keiver.madara.web.base.utils.ResultSet;
import com.keiver.madara.web.service.MngUserService;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户相关控制器
 * @author prd-ckf
 * @version $Id: MngUserController.java, v 0.1 2018年4月12日 下午8:25:38 prd-ckf Exp $
 */
@Controller
@RequestMapping("/sys/user")
public class MngUserController {

    private static final Logger logger = LoggerFactory.getLogger(MngUserController.class);

    @Resource
    private MngUserService mngUserService;

    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "sys/user";
    }

    /**
     * 获取用户列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResultSet list(MngUserQueryRequest request) {
        LogUtil.info(logger, "[Controller]收到#Web获取用户列表#请求，request={0}", request);

        PageInfo<MngUser> mngUserList = mngUserService.queryList(request);

        LogUtil.info(logger, "[Controller]处理#Web获取用户列表#成功，mngUserList={0}", mngUserList);
        return ResultSet.success().put("page", mngUserList);
    }

    /**
     * 获取个人用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public ResultSet info(long userId) {
        LogUtil.info(logger, "[Controller]收到#Web获取个人用户信息#请求，userId={0}", userId);

        MngUser mngUser = mngUserService.getUserByID(userId);
        //擦除密码摘要
        mngUser.setPassword(null);

        LogUtil.info(logger, "[Controller]处理#Web获取个人用户信息#成功，mngUser={0}", mngUser);
        return ResultSet.success().put("user", mngUser);
    }

    /**
     * 新增用户
     * @param mngUser
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet add(@RequestBody MngUser mngUser) {

        LogUtil.info(logger, "[Controller]收到#Web新增用户#请求，mngUser={0}", mngUser);

        mngUser.validate();

        mngUserService.addUserInfo(mngUser);

        LogUtil.info(logger, "[Controller]处理#Web新增用户#成功");

        return ResultSet.success();
    }

    /**
     * 更改用户信息
     * @param mngUser
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet update(@RequestBody MngUser mngUser) {

        LogUtil.info(logger, "[Controller]收到#Web修改用户信息#请求，mngUser={0}", mngUser);

        mngUser.validate();

        mngUserService.updateUserInfo(mngUser);

        LogUtil.info(logger, "[Controller]处理#Web修改用户信息#成功");

        return ResultSet.success();
    }

    /**
     * 批量注销用户
     * @param userIdList
     * @return
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet close(@RequestBody List<Long> userIdList) {

        LogUtil.info(logger, "[Controller]收到#Web批量注销用户#请求，userIdList={0}", userIdList);

        mngUserService.closeUser(userIdList);

        LogUtil.info(logger, "[Controller]处理#Web批量注销用户#成功");

        return ResultSet.success();
    }

    /**
     * 批量解冻用户
     * @param userIdList
     * @return
     */
    @RequestMapping(value = "/unfreeze", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet unfreeze(@RequestBody List<Long> userIdList) {

        LogUtil.info(logger, "[Controller]收到#Web批量解冻用户#请求，userIdList={0}", userIdList);

        mngUserService.unfreezeUser(userIdList);

        LogUtil.info(logger, "[Controller]处理#Web批量解冻用户#成功");

        return ResultSet.success();
    }

    /**
     * 重置用户密码
     * @param userId
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet resetPassword(long userId) {

        LogUtil.info(logger, "[Controller]收到#Web重置用户密码#请求，userId={0}", userId);

        mngUserService.resetPassword(userId);

        LogUtil.info(logger, "[Controller]处理#Web重置用户密码#成功");

        return ResultSet.success();
    }

    /**
     * 修改用户密码
     * @param oldPassword
     * @param newPassword
     * @param newPasswordConfirm
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultSet updatePassword(String oldPassword, String newPassword, String newPasswordConfirm) {

        LogUtil.info(logger, "[Controller]收到#Web修改用户密码#请求，oldPassword={0},newPassword={1},newPasswordConfirm={2}", oldPassword, newPassword, newPasswordConfirm);

        RuntimeAssertUtil.assertNotBlank(oldPassword, "旧密码不能为空");
        RuntimeAssertUtil.assertNotBlank(newPassword, "新密码不能为空");
        RuntimeAssertUtil.assertNotBlank(newPasswordConfirm, "新密码确认不能为空");
        RuntimeAssertUtil.assertTrue(StringUtils.length(oldPassword) <= 20, "旧密码不能超过20个字符");
        RuntimeAssertUtil.assertTrue(StringUtils.length(newPassword) <= 20, "新密码不能超过20个字符");
        RuntimeAssertUtil.assertTrue(StringUtils.length(newPasswordConfirm) <= 20, "新密码确认不能超过20个字符");
        RuntimeAssertUtil.assertEquals(newPassword, newPasswordConfirm, "两次新密码输入不一致");

        mngUserService.updatePassword(oldPassword, newPassword);

        LogUtil.info(logger, "[Controller]处理#Web修改用户密码#成功");

        return ResultSet.success();
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @RequestMapping(value = "/myInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultSet myInfo() {
        Map<String, String> info = new HashedMap<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        info.put("loginName", shiroUser.getLoginName());
        info.put("userName", shiroUser.getName());
        return ResultSet.success().put("info", info);
    }
}
