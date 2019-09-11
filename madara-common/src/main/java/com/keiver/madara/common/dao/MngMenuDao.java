package com.keiver.madara.common.dao;

import com.keiver.madara.common.domain.MngMenu;

import java.util.List;


public interface MngMenuDao {
    int deleteByPrimaryKey(Long id);

    int insert(MngMenu record);

    int insertSelective(MngMenu record);

    MngMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MngMenu record);

    int updateByPrimaryKey(MngMenu record);

    List<MngMenu> queryList();

    List<MngMenu> queryListByRoleId(Long roleId);

    List<MngMenu> queryListByParentCode(String parentCode);
}