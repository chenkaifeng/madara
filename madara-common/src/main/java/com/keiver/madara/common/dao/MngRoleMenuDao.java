package com.keiver.madara.common.dao;

import com.keiver.madara.common.domain.MngRoleMenu;

import java.util.List;
import java.util.Map;


public interface MngRoleMenuDao {
    int deleteByPrimaryKey(Long id);

    int insert(MngRoleMenu record);

    int insertSelective(MngRoleMenu record);

    MngRoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MngRoleMenu record);

    int updateByPrimaryKey(MngRoleMenu record);

    List<Long> getMenuIdListByRoleId(long roleId);

    int deleteByRoleId(long roleId);

    int insertRoleMenu(Map<String, Object> paraMap);
}