package com.keiver.madara.web.service;

import com.keiver.madara.common.domain.MngMenu;

import java.util.List;


/**
 * 管理员菜单管理服务接口
 * 
 * @author 51
 * @version $Id: MngMenuService.java, v 0.1 2018年4月4日 上午10:56:55 51 Exp $
 */
public interface MngMenuService {

    /**
     * 根据用户ID返回菜单列表
     * @param userId    用户ID
     * @return          菜单列表
     */
    List<MngMenu> getMenuListByUserId(Long userId);

    /**
     * 获取所有菜单
     * @return
     */
    List<MngMenu> getMenuList();

    /**
     * 获取某角色拥有的菜单
     * @return
     */
    List<MngMenu> getMenuListByRoleId(long roleId);

}
