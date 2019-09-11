package com.keiver.madara.web.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.keiver.madara.common.dao.MngRoleMenuDao;
import com.keiver.madara.web.repository.MngRoleMenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * 角色-菜单仓储服务实现类
 * 
 * @author prd-ckf
 * @version $Id: MngRoleMenuRepositoryImpl.java, v 0.1 2018年4月4日 下午6:07:46 prd-ckf Exp $
 */
@Repository
public class MngRoleMenuRepositoryImpl implements MngRoleMenuRepository {

    private static final Logger logger = LoggerFactory.getLogger(MngRoleMenuRepositoryImpl.class);

    @Resource
    private MngRoleMenuDao mngRoleMenuDao;

    @Override
    public List<Long> getMenuIdListByRoleId(long roleId) {
        return mngRoleMenuDao.getMenuIdListByRoleId(roleId);
    }

    @Override
    public int deleteByRoleId(long roleId) {
        return mngRoleMenuDao.deleteByRoleId(roleId);
    }

    @Override
    public int insertRoleMenu(long roleId, List<Long> menuIdList) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("menuIdList", menuIdList);
        return mngRoleMenuDao.insertRoleMenu(map);
    }

}
