package com.keiver.madara.web.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;


import com.github.pagehelper.PageInfo;
import com.keiver.madara.common.domain.MngUser;
import com.keiver.madara.common.request.mng.MngUserQueryRequest;

/**
 * 管理员用户仓储服务接口
 * 
 * @author Chenkf
 * @version $Id: MngUserRepository.java, v 0.1 2018年3月28日 下午3:30:12 51 Exp $
 */
public interface MngUserRepository {

    /**
     * 根据用户ID返回用户实例
     * 
     * @param userId    用户ID
     * @return          用户实例
     */
    MngUser getUserByID(Long userId);

    /**
     * 根据用户编码返回用户实例
     * 
     * @param userCode
     * @return
     */
    MngUser getUserByUserCode(String userCode);

    /**
     * 根据用户ID获取该用户权限集
     * 
     * @param userId
     * @return
     */
    Set<String> getPermsSetByUserId(Long userId);

    /**
     * 根据用户ID获取该用户菜单ID集合
     * 
     * @param userId
     * @return
     */
    List<Long> getMenuIdListByUserId(Long userId);

    /**
     * 分页查询用户列表
     * 
     * @param map
     * @return
     */
    PageInfo<MngUser> queryList(MngUserQueryRequest request);

    /**
     * 存储用户
     * @param mngUser   用户实体
     * @param createUserCode    创建人编码
     * @param password  密码摘要
     * @return  主键
     */
    long saveMngUser(MngUser mngUser, String createUserCode, String password);

    /**
     * 更新用户
     * 
     * @param mngUser
     * @return
     */
    int updateMngUser(MngUser mngUser);

    /**
     * 通过用户名和密码获取用户
     * @param userCode
     * @param password
     * @return
     */
    MngUser getUserByCodeAndPassword(String userCode, String password);

    /**
     * 批量修改用户状态
     * @param userIdList    用户ID列表
     * @param status    修改后状态
     * @param gmtUpdate 更新时间
     * @param isUnfreeze 是否是解除冻结
     * @return
     */
    int updateUserStatus(List<Long> userIdList, String status, Date gmtUpdate, boolean isUnfreeze);

    /**
     * 根据用户ID列表获取用户列表
     * @param userIdList    用户ID列表
     * @return
     */
    List<MngUser> queryListByUserIdList(List<Long> userIdList);

    /**
     * 根据角色ID列表获取用户列表
     * @param roleId    角色ID
     * @return
     */
    List<MngUser> queryListByRoleId(long roleId);
}
