package com.keiver.madara.web.repository;

import java.util.List;

/**
 * 角色-菜单仓储服务
 * 
 * @author prd-ckf
 * @version $Id: MngRoleMenuRepository.java, v 0.1 2018年4月4日 下午6:03:59 prd-ckf Exp $
 */
public interface MngRoleMenuRepository {

    /**
     * 根据角色ID获取其菜单ID列表
     * 
     * @param roleId
     * @return
     */
    List<Long> getMenuIdListByRoleId(long roleId);

    /**
     * 根据角色ID删除其菜单ID列表
     * 
     * @param roleId
     * @return
     */
    int deleteByRoleId(long roleId);

    /**
     * 新增角色与菜单ID列表对应关系
     * 
     * @param roleId    角色ID
     * @param menuIdList    菜单ID列表
     * @return
     */
    int insertRoleMenu(long roleId, List<Long> menuIdList);

}
