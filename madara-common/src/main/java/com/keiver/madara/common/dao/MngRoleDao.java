package com.keiver.madara.common.dao;

import com.keiver.madara.common.domain.MngRole;
import com.keiver.madara.common.request.mng.MngRoleQueryRequest;

import java.util.List;
import java.util.Map;


public interface MngRoleDao {
    int deleteByPrimaryKey(Long id);

    int insert(MngRole record);

    int insertSelective(MngRole record);

    MngRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MngRole record);

    int updateByPrimaryKey(MngRole record);

    List<MngRole> listQueryMngRoleByPage(MngRoleQueryRequest request);

    int updateRoleStatus(Map<String, Object> paraMap);

    List<MngRole> queryListByRoleIdList(Map<String, Object> map);
}