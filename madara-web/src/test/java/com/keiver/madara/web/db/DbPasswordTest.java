package com.keiver.madara.web.db;

import com.alibaba.druid.filter.config.ConfigTools;
import com.keiver.madara.common.utils.LogUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 数据库用户密码加密测试类
 *
 * @author: Chenkf
 * @create: 2019-09-16 14:27:47
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:Spring-config.xml"})
public class DbPasswordTest {

    public static final Logger logger = LoggerFactory.getLogger(DbPasswordTest.class);

    /**
     * 数据库密码明文
     */
    private static String YOUR_PASSWORD = "123456";

    @Test
    public void encryptDbPassword() throws Exception {

        String[] keyPair = ConfigTools.genKeyPair(512);
        //私钥
        String privateKey = keyPair[0];
        //公钥
        String publicKey = keyPair[1];
        //用私钥加密后的密文
        String password = ConfigTools.encrypt(privateKey, YOUR_PASSWORD);

        LogUtil.info(logger, "密码[{0}]的加密信息如下：", YOUR_PASSWORD);
        LogUtil.info(logger, "privateKey:{0}", privateKey);
        LogUtil.info(logger, "publicKey:{0}", publicKey);
        LogUtil.info(logger, "加密后的password:{0}", password);

        //验证解密
        String decryptPassword = ConfigTools.decrypt(publicKey, password);
        Assert.assertEquals(YOUR_PASSWORD, decryptPassword);
    }
}
