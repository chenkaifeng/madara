package com.keiver.madara.web.repository;

import com.keiver.madara.common.domain.MngMenu;

import java.util.List;


/**
 * 管理员菜单仓储服务接口
 * 
 * @author Chenkf
 * @version $Id: MngMenuRepository.java, v 0.1 2018年4月4日 上午10:54:27 51 Exp $
 */
public interface MngMenuRepository {

    /**
     * 根据角色ID返回菜单列表
     * 
     * @param roleId    角色ID
     * @return          菜单列表
     */
    List<MngMenu> getMenuListByRoleId(long roleId);

    /**
     * 获取所有菜单
     * 
     * @return
     */
    List<MngMenu> getMenuList();

    /**
     * 根据父菜单Code返回其子菜单列表
     * 
     * @param parentCode
     * @return
     */
    List<MngMenu> getMenuListByParentCode(String parentCode);

}
