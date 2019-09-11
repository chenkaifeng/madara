package com.keiver.madara.web.repository;

import java.util.List;

/**
 * 用户-角色仓储服务接口
 * 
 * @author prd-ckf
 * @version $Id: MngUserRoleRepository.java, v 0.1 2018年4月9日 上午9:54:29 prd-ckf Exp $
 */
public interface MngUserRoleRepository {

    /**
     * 根据用户ID获取其角色ID列表
     * 
     * @param userId
     * @return
     */
    List<Long> getRoleIdListByUserId(long userId);

    /**
     * 根据用户ID删除其角色ID列表
     * 
     * @param userId
     * @return
     */
    int deleteByUserId(long userId);

    /**
     * 新增用户与角色ID列表对应关系
     * 
     * @param userId    用户ID
     * @param roleIdList    角色ID列表
     * @return
     */
    int insertUserRole(long userId, List<Long> roleIdList);

}
