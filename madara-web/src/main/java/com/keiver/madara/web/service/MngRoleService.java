package com.keiver.madara.web.service;

import java.util.List;


import com.github.pagehelper.PageInfo;
import com.keiver.madara.common.domain.MngRole;
import com.keiver.madara.common.request.mng.MngRoleQueryRequest;

/**
 * 角色服务
 * 
 * @author prd-ckf
 * @version $Id: MngRoleService.java, v 0.1 2018年4月3日 下午9:32:51 prd-ckf Exp $
 */
public interface MngRoleService {

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
     * 根据roleId获取角色信息
     * @param roleId
     * @return
     */
    MngRole getRoleInfo(long roleId);

    /**
     * 更新角色信息
     * @param mngRole
     */
    void updateRoleInfo(MngRole mngRole);

    /**
     * 新增角色信息
     * @param mngRole
     */
    void addRoleInfo(MngRole mngRole);

    /**
     * 批量注销角色
     * @param roleIdList
     */
    void closeRole(List<Long> roleIdList);

}
