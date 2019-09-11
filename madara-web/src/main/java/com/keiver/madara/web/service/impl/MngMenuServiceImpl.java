package com.keiver.madara.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.keiver.madara.common.domain.MngMenu;
import com.keiver.madara.web.repository.MngMenuRepository;
import com.keiver.madara.web.repository.MngUserRepository;
import com.keiver.madara.web.service.MngMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * 管理员菜单管理服务实现类
 * 
 * @author 51
 * @version $Id: MngMenuServiceImpl.java, v 0.1 2018年3月28日 下午3:38:25 51 Exp $
 */
@Service
public class MngMenuServiceImpl implements MngMenuService {

    @Resource
    private MngMenuRepository mngMenuRepository;

    @Resource
    private MngUserRepository mngUserRepository;

    @Override
    public List<MngMenu> getMenuListByUserId(Long userId) {
        //1.根据用户编号获取其拥有的菜单编号列表
        List<Long> menuIdList = mngUserRepository.getMenuIdListByUserId(userId);
        //2.查询根菜单下的一级目录列表(0表示根节点,根节点的子节点即是一级目录)
        List<MngMenu> firstMenuList = mngMenuRepository.getMenuListByParentCode("0");
        //3.获得该用户拥有的一级目录
        List<MngMenu> userFirstMenuList = new ArrayList<>();
        for (MngMenu menu : firstMenuList) {
            if (menuIdList.contains(menu.getId())) {
                userFirstMenuList.add(menu);
            }
        }
        //4.递归获取用户子菜单
        return getMenuTree(userFirstMenuList, menuIdList);
    }

    @Override
    public List<MngMenu> getMenuList() {
        return mngMenuRepository.getMenuList();
    }

    private List<MngMenu> getMenuTree(List<MngMenu> menuList, List<Long> menuIdList) {
        List<MngMenu> subMenuList = new ArrayList<MngMenu>();
        for (MngMenu entity : menuList) {
            if (StringUtils.equals(entity.getType(), "CATALOG")) {
                List<MngMenu> nextMenuList = mngMenuRepository.getMenuListByParentCode(entity.getMenuCode());
                List<MngMenu> userMenuList = new ArrayList<>();
                for (MngMenu menu : nextMenuList) {
                    if (menuIdList.contains(menu.getId())) {
                        userMenuList.add(menu);
                    }
                }
                entity.setList(getMenuTree(userMenuList, menuIdList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }

    @Override
    public List<MngMenu> getMenuListByRoleId(long roleId) {
        return mngMenuRepository.getMenuListByRoleId(roleId);
    }
}
