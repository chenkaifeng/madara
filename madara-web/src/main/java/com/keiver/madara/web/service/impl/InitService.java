/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package com.keiver.madara.web.service.impl;

import javax.annotation.PostConstruct;

import com.keiver.madara.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 系统初始化Service
 * 
 * @author 51
 * @version $Id: InitService.java, v 0.1 2018年8月30日 上午10:56:17 51 Exp $
 */
@Service
public class InitService {
    public static final Logger         logger = LoggerFactory.getLogger(InitService.class);

    @PostConstruct
    public void init() {
        LogUtil.info(logger, "开始预加载系统参数");
        LogUtil.info(logger, "系统参数预加载完成");
    }

}
