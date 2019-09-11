package com.keiver.madara.web.base.utils;

import java.lang.reflect.Field;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.keiver.madara.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.ContextLoader;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;
import com.alibaba.dubbo.registry.support.FailbackRegistry;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistryFactory;
import com.alibaba.dubbo.remoting.exchange.support.header.HeaderExchangeClient;
import com.alibaba.dubbo.rpc.Protocol;

/**
 * 在servlet生命周期结束时，关闭dubbo的netty服务
 * @author Chenkf
 * @version $Id: DestroyServlet.java, v 0.1 2018年4月3日 下午2:42:07 Chenkf Exp $
 */
public class DestroyServlet extends HttpServlet {

    /**  */
    private static final long   serialVersionUID = 3668149090208719012L;

    /** 日志 */
    private static final Logger logger           = LoggerFactory.getLogger(DestroyServlet.class);

    @Override
    public void init() {
        logger.info("启动 dubbo 关闭 的servlet");
    }

    public DestroyServlet() {
        super();
    }

    @Override
    public void destroy() {
        stopAll();
        System.gc();
    }

    public void stopAll() {
        LogUtil.debug(logger, "web应用生命周期结束管理--开始!");
        destroyDBDriver();
        stopDubboRemotingHeartBeat();
        stopDubboRegistries();
        stopDubboServer();
        stopDubboClient();
        stopDataSource();
        LogUtil.debug(logger, "web应用生命周期结束管理--结束!");
    }

    private void stopDubboRemotingHeartBeat() {
        try {
            Field declaredField = HeaderExchangeClient.class.getDeclaredField("scheduled");
            declaredField.setAccessible(true);
            ScheduledThreadPoolExecutor scheduled = (ScheduledThreadPoolExecutor) declaredField.get(new Object());
            scheduled.shutdownNow();
        } catch (NoSuchFieldException e) {
            LogUtil.error(e, logger, "stopDubboRemotingHeartBeat 异常！");
        } catch (SecurityException e) {
            LogUtil.error(e, logger, "stopDubboRemotingHeartBeat 异常！");
        } catch (IllegalArgumentException e) {
            LogUtil.error(e, logger, "stopDubboRemotingHeartBeat 异常！");
        } catch (IllegalAccessException e) {
            LogUtil.error(e, logger, "stopDubboRemotingHeartBeat 异常！");
        }
    }

    public static void stopDubboServer() {
        logger.info("执行 dubbo 关闭 服务 开始");
        try {
            //server
            for (Protocol protocol : ObjectFactory.getObject(Protocol.class)) {
                try {
                    LogUtil.info(logger, "开始获取到dubbo服务");
                    if (protocol != null) {
                        LogUtil.info(logger, "已经获取到{0}，关闭获取到dubbo服务");
                        protocol.destroy();
                    }
                } catch (Exception e) {
                    LogUtil.error(e, logger, "执行 dubbo 关闭 服务异常");
                }
            }
            LogUtil.error(logger, "执行关闭  dubbo server 结束");
        } catch (Exception e) {
            LogUtil.error(e, logger, "执行 dubbo 关闭 服务异常");
        }
        logger.info("执行 dubbo 关闭 服务 结束");
    }

    private static void stopDubboRegistries() {
        //FailbackRegistry=>DubboRegistryFailedRetryTimer ; AbstractRegistry => DubboSaveRegistryCache
        Collection<Registry> registries = ZookeeperRegistryFactory.getRegistries();
        for (Registry registry : registries) {
            if (registry instanceof ZookeeperRegistry) {
                ZookeeperRegistry zr = (ZookeeperRegistry) registry;
                zr.destroy();

                //关闭定时任务 retryExecutor
                Field retryExecutorField = ReflectionUtils.findField(FailbackRegistry.class, "retryExecutor");
                retryExecutorField.setAccessible(true);
                ScheduledExecutorService retryExecutor = (ScheduledExecutorService) ReflectionUtils.getField(retryExecutorField, zr);
                retryExecutor.shutdownNow();

                //关闭定时任务 registryCacheExecutor
                Field registryCacheExecutorField = ReflectionUtils.findField(AbstractRegistry.class, "registryCacheExecutor");
                registryCacheExecutorField.setAccessible(true);
                ExecutorService registryCacheExecutor = (ExecutorService) ReflectionUtils.getField(registryCacheExecutorField, zr);
                registryCacheExecutor.shutdownNow();
            }
        }
    }

    public void destroyDBDriver() {
        String prefix = DestroyServlet.class.getSimpleName() + " destroyDBDriver() ";
        ServletContext ctx = this.getServletContext();
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (Exception e) {
            ctx.log(prefix + "Exception caught while deregistering JDBC drivers", e);
        }
        ctx.log(prefix + "complete");
    }

    private static void stopDubboClient() {
        //client 
        LogUtil.error(logger, "执行关闭  dubbo client 开始");
        try {
            AbstractRegistryFactory.destroyAll();
        } catch (Exception e) {
            LogUtil.error(e, logger, "stopDubboClient 异常！");
        }
    }

    private static void stopDataSource() {
        //datasource
        try {
            DruidDataSource ds = (DruidDataSource) ContextLoader.getCurrentWebApplicationContext().getBean("dataSource");
            if (ds != null) {
                ds.close();
            }
        } catch (Exception e) {
            LogUtil.error(e, logger, "stopDataSource 异常！");
        }
    }

    public static void stopThreadAll() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组  
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数加倍  
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 获取根线程组的所有线程  
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size  
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        System.out.println("Thread list size == " + list.length);
        for (Thread thread : list) {
            try {
                logger.info("执行关闭线程【{0}】操作");
                LogUtil.info(logger, "执行关闭线程【{0}】操作", thread.getName());
                thread.interrupt();
            } catch (Exception e) {
                LogUtil.error(e, logger, "执行关闭线程【{0}】操作异常：{1}", thread.getName(), e.getMessage());
            }
        }
    }

    static class ObjectFactory {
        public static <E> Iterable<E> getObject(final Class<E> clazz) {
            return new Iterable<E>() {
                IterM<E> it = new IterM<>(clazz);

                @Override
                public Iterator<E> iterator() {
                    return it;
                }
            };
        }

        static class IterM<E> implements Iterator<E> {
            @SuppressWarnings("rawtypes")
            ExtensionLoader  extensionLoader  = null;
            Set<String>      loadedExtensions = null;
            Iterator<String> ExtNameiterator  = null;
            String           temp             = null;

            @SuppressWarnings({ "unchecked", "hiding" })
            public <E> IterM(Class<E> clazz) {
                extensionLoader = ExtensionLoader.getExtensionLoader(clazz);
                loadedExtensions = extensionLoader.getLoadedExtensions();
                ExtNameiterator = loadedExtensions.iterator();
            }

            @Override
            public boolean hasNext() {
                return ExtNameiterator.hasNext();
            }

            @SuppressWarnings("unchecked")
            @Override
            public E next() {
                return (E) extensionLoader.getExtension(ExtNameiterator.next());
            }

            @Override
            public void remove() {
            }

        }
    }

}
