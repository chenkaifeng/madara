package com.keiver.madara.web.service;

import java.util.List;
import java.util.Set;


import com.github.pagehelper.PageInfo;
import com.keiver.madara.common.domain.MngUser;
import com.keiver.madara.common.request.mng.MngUserQueryRequest;

/**
 * 管理员用户管理服务接口
 * 
 * @author 51
 * @version $Id: MngUserService.java, v 0.1 2018年3月28日 下午3:37:54 51 Exp $
 */
public interface MngUserService {

    /**
     * 根据用户ID返回用户实例
     * @param userId    用户ID
     * @return          用户实例
     */
    MngUser getUserByID(Long userId);

    /**
     * 根据用户编码返回用户实例
     * @param userCode    用户编码(code)
     * @return          用户实例
     */
    MngUser getUserByUserCode(String userCode);

    /**
     * 根据用户ID获取该用户权限集
     * @param userId
     * @return
     */
    Set<String> getPermsSetByUserId(Long userId);

    /**
     * 分页查询用户列表
     * @param map
     * @return
     */
    PageInfo<MngUser> queryList(MngUserQueryRequest request);

    /**
     * 更新用户信息(包含角色关系)
     * @param mngRole
     */
    void updateUserInfo(MngUser mngUser);

    /**
     * 新增用户信息(包含角色关系)
     * @param mngRole
     */
    void addUserInfo(MngUser mngUser);

    /**
     * 根据用户名和密码查询用户
     * @param userCode
     * @param password
     * @return
     */
    MngUser getUserByCodeAndPassword(String userCode, String password);

    /**
     * 用户登陆密码错误时，更新密码错误次数
     * @param userCode
     * @return
     */
    String updatePasswordWrongTimes(String userCode);

    /**
     * 批量注销用户
     * @param userIdList
     */
    void closeUser(List<Long> userIdList);

    /**
     * 批量解冻用户
     * @param userIdList
     */
    void unfreezeUser(List<Long> userIdList);

    /**
     * 重置用户密码
     * @param userId
     */
    void resetPassword(long userId);

    /**
     * 修改用户密码
     * @param oldPassword
     * @param newPassword
     */
    void updatePassword(String oldPassword, String newPassword);
}
