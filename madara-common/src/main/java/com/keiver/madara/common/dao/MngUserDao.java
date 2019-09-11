package com.keiver.madara.common.dao;

import com.keiver.madara.common.domain.MngUser;
import com.keiver.madara.common.request.mng.MngUserQueryRequest;

import java.util.List;
import java.util.Map;


public interface MngUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(MngUser record);

    int insertSelective(MngUser record);

    MngUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MngUser record);

    int updateByPrimaryKey(MngUser record);

    List<String> queryPermissions(Long id);

    List<MngUser> listQueryMngUserByPage(MngUserQueryRequest queryRequest);

    List<MngUser> queryListByUserIdList(Map<String, Object> map);

    List<MngUser> queryListByRoleId(Long roleId);

    List<Long> queryMenuIdListByUserId(Long id);

    MngUser getUserByUserCode(String userCode);

    MngUser getUserByMap(Map<String, Object> paraMap);

    int updateUserStatus(Map<String, Object> paraMap);
}