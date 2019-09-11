package com.keiver.madara.web.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.keiver.madara.common.dao.MngUserRoleDao;
import com.keiver.madara.web.repository.MngUserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * 用户-角色仓储服务实现类
 * 
 * @author prd-ckf
 * @version $Id: MngUserRoleRepositoryImpl.java, v 0.1 2018年4月9日 上午9:58:01 prd-ckf Exp $
 */
@Repository
public class MngUserRoleRepositoryImpl implements MngUserRoleRepository {

    private static final Logger logger = LoggerFactory.getLogger(MngUserRoleRepositoryImpl.class);

    @Resource
    private MngUserRoleDao mngUserRoleDao;

    @Override
    public List<Long> getRoleIdListByUserId(long userId) {
        return mngUserRoleDao.getRoleIdListByUserId(userId);
    }

    @Override
    public int deleteByUserId(long userId) {
        return mngUserRoleDao.deleteByUserId(userId);
    }

    @Override
    public int insertUserRole(long userId, List<Long> roleIdList) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIdList", roleIdList);
        return mngUserRoleDao.insertUserRole(map);
    }

}
