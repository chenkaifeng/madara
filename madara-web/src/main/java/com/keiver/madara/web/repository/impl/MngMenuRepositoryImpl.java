package com.keiver.madara.web.repository.impl;

import java.util.List;

import javax.annotation.Resource;

import com.keiver.madara.common.dao.MngMenuDao;
import com.keiver.madara.common.domain.MngMenu;
import com.keiver.madara.web.repository.MngMenuRepository;
import org.springframework.stereotype.Repository;


/**
 * 管理员菜单仓储服务实现类
 * 
 * @author Chenkf
 * @version $Id: MngMenuRepositoryImpl.java, v 0.1 2018年4月4日 上午10:55:44 51 Exp $
 */
@Repository
public class MngMenuRepositoryImpl implements MngMenuRepository {

    @Resource
    private MngMenuDao mngMenuDao;

    @Override
    public List<MngMenu> getMenuListByRoleId(long roleId) {
        return mngMenuDao.queryListByRoleId(roleId);
    }

    @Override
    public List<MngMenu> getMenuList() {
        return mngMenuDao.queryList();
    }

    @Override
    public List<MngMenu> getMenuListByParentCode(String parentCode) {
        return mngMenuDao.queryListByParentCode(parentCode);
    }

}
