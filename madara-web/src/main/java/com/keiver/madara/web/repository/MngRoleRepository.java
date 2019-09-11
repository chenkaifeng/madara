package com.keiver.madara.web.repository;

import java.util.Date;
import java.util.List;


import com.github.pagehelper.PageInfo;
import com.keiver.madara.common.domain.MngRole;
import com.keiver.madara.common.request.mng.MngRoleQueryRequest;

/**
 * 角色仓储服务接口
 * 
 * @author prd-ckf
 * @version $Id: MngRoleRepository.java, v 0.1 2018年4月3日 下午9:44:30 prd-ckf Exp $
 */
public interface MngRoleRepository {

    /**
     * 分页查询角色列表
     * @param request
     * @return
     */
    PageInfo<MngRole> queryListByPage(MngRoleQueryRequest request);

    /**
     * 查询角色列表
     * @param request
     * @return
     */
    List<MngRole> queryList(MngRoleQueryRequest request);

    /**
     * 根据角色列表获取角色信息
     * @param roleId
     * @return
     */
    MngRole getRoleInfo(long roleId);

    /**
     * 更新角色信息
     * @param mngRole   角色实体
     * @param updateUserCode 修改人用户编码
     * @return
     */
    int updateRole(MngRole mngRole, String updateUserCode);

    /**
     * 新增角色信息
     * @param mngRole   角色实体
     * @param createUserCode 新增人用户编码
     * @return  主键ID
     */
    long addRole(MngRole mngRole, String createUserCode);

    /**
     * 更新角色状态
     * @param roleIdList    角色ID列表
     * @param status    更新的状态   
     * @param gmtUpdate 更新时间
     * @return
     */
    int updateRoleStatus(List<Long> roleIdList, String status, Date gmtUpdate, String updateUserCode);

    /**
     * 根据角色ID列表获取角色列表
     * @param roleIdList    角色ID列表
     * @return
     */
    List<MngRole> queryListByRoleIdList(List<Long> roleIdList);
}
