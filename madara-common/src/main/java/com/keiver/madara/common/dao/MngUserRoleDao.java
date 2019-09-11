package com.keiver.madara.common.dao;

import com.keiver.madara.common.domain.MngUserRole;

import java.util.List;
import java.util.Map;


public interface MngUserRoleDao {
    int deleteByPrimaryKey(Long id);

    int insert(MngUserRole record);

    int insertSelective(MngUserRole record);

    MngUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MngUserRole record);

    int updateByPrimaryKey(MngUserRole record);

    List<Long> getRoleIdListByUserId(long userId);

    int deleteByUserId(long userId);

    int insertUserRole(Map<String, Object> paraMap);
}