package com.keiver.madara.web.repository.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.keiver.madara.common.dao.MngRoleDao;
import com.keiver.madara.common.domain.MngRole;
import com.keiver.madara.common.enums.CommonEnum;
import com.keiver.madara.common.request.mng.MngRoleQueryRequest;
import com.keiver.madara.web.repository.MngRoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 角色仓储服务实现
 * 
 * @author prd-ckf
 * @version $Id: MngRoleRepositoryImpl.java, v 0.1 2018年4月3日 下午9:45:30 prd-ckf Exp $
 */
@Repository
public class MngRoleRepositoryImpl implements MngRoleRepository {

    private static final Logger logger = LoggerFactory.getLogger(MngRoleRepositoryImpl.class);

    @Resource
    private MngRoleDao mngRoleDao;

    @Override
    public PageInfo<MngRole> queryListByPage(MngRoleQueryRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        if (StringUtils.isNotBlank(request.getSidx())) {
            PageHelper.orderBy(request.getSidx() + " " + request.getOrder());
        }
        List<MngRole> mngRoleList = mngRoleDao.listQueryMngRoleByPage(request);
        PageInfo<MngRole> pageInfo = new PageInfo<MngRole>(mngRoleList);
        return pageInfo;
    }

    @Override
    public MngRole getRoleInfo(long roleId) {
        return mngRoleDao.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateRole(MngRole mngRole, String updateUserCode) {
        mngRole.setUpdateUserCode(updateUserCode);
        mngRole.setGmtUpdate(null);
        return mngRoleDao.updateByPrimaryKeySelective(mngRole);
    }

    @Override
    public long addRole(MngRole mngRole, String createUserCode) {
        mngRole.setStatus(CommonEnum.NORMAL.getCode());
        mngRole.setCreateUserCode(createUserCode);
        mngRole.setGmtCreate(new Date());
        mngRole.setGmtUpdate(new Date());
        mngRoleDao.insertSelective(mngRole);
        return mngRole.getId();
    }

    @Override
    public List<MngRole> queryList(MngRoleQueryRequest request) {
        return mngRoleDao.listQueryMngRoleByPage(request);
    }

    @Override
    public int updateRoleStatus(List<Long> roleIdList, String status, Date gmtUpdate, String updateUserCode) {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        paraMap.put("roleIdList", roleIdList);
        paraMap.put("status", status);
        paraMap.put("updateUserCode", updateUserCode);
        return mngRoleDao.updateRoleStatus(paraMap);
    }

    @Override
    public List<MngRole> queryListByRoleIdList(List<Long> roleIdList) {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        paraMap.put("roleIdList", roleIdList);
        return mngRoleDao.queryListByRoleIdList(paraMap);
    }
}
