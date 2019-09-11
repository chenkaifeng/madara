package com.keiver.madara.web.repository.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.keiver.madara.common.dao.MngUserDao;
import com.keiver.madara.common.domain.MngUser;
import com.keiver.madara.common.request.mng.MngUserQueryRequest;
import com.keiver.madara.web.repository.MngUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
 * 管理员用户仓储服务实现类
 * 
 * @author Chenkf
 * @version $Id: MngUserRepositoryImpl.java, v 0.1 2018年4月4日 上午10:55:58 51 Exp $
 */
@Repository
public class MngUserRepositoryImpl implements MngUserRepository {

    private static final Logger logger = LoggerFactory.getLogger(MngUserRepositoryImpl.class);

    @Resource
    private MngUserDao mngUserDao;

    @Override
    public MngUser getUserByID(Long userId) {
        return mngUserDao.selectByPrimaryKey(userId);
    }

    @Override
    public Set<String> getPermsSetByUserId(Long userId) {
        List<String> permsList = mngUserDao.queryPermissions(userId);
        Set<String> permsSet = new HashSet<String>();
        if (permsList != null && permsList.size() > 0) {
            for (String perms : permsList) {
                if (StringUtils.isNotBlank(perms)) {
                    permsSet.addAll(Arrays.asList(perms.trim().split(",")));
                }
            }
        }
        return permsSet;
    }

    @Override
    public PageInfo<MngUser> queryList(MngUserQueryRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        if (StringUtils.isNotBlank(request.getSidx())) {
            PageHelper.orderBy(request.getSidx() + " " + request.getOrder());
        }
        List<MngUser> mngUserList = mngUserDao.listQueryMngUserByPage(request);
        PageInfo<MngUser> pageInfo = new PageInfo<MngUser>(mngUserList);
        return pageInfo;
    }

    @Override
    public long saveMngUser(MngUser mngUser, String createUserCode, String password) {
        mngUser.setStatus("00");
        mngUser.setPassword(password);
        mngUser.setCreateUserCode(createUserCode);
        mngUserDao.insertSelective(mngUser);
        return mngUser.getId();
    }

    @Override
    public List<Long> getMenuIdListByUserId(Long userId) {
        return mngUserDao.queryMenuIdListByUserId(userId);
    }

    @Override
    public MngUser getUserByUserCode(String userCode) {
        return mngUserDao.getUserByUserCode(userCode);
    }

    @Override
    public int updateMngUser(MngUser mngUser) {
        mngUser.setGmtUpdate(null);
        return mngUserDao.updateByPrimaryKeySelective(mngUser);
    }

    @Override
    public MngUser getUserByCodeAndPassword(String userCode, String password) {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        paraMap.put("userCode", userCode);
        paraMap.put("password", password);

        return mngUserDao.getUserByMap(paraMap);
    }

    @Override
    public int updateUserStatus(List<Long> userIdList, String status, Date gmtUpdate, boolean isUnfreeze) {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        paraMap.put("userIdList", userIdList);
        paraMap.put("status", status);
        if (isUnfreeze) {
            paraMap.put("loginFailNum", (short) 0);
        }
        return mngUserDao.updateUserStatus(paraMap);
    }

    @Override
    public List<MngUser> queryListByUserIdList(List<Long> userIdList) {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        paraMap.put("userIdList", userIdList);
        return mngUserDao.queryListByUserIdList(paraMap);
    }

    @Override
    public List<MngUser> queryListByRoleId(long roleId) {
        return mngUserDao.queryListByRoleId(roleId);
    }
}
