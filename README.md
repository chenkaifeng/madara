# madara简介
1) 基于spring框架开发的分布式系统
2) 包含一个web系统、业务系统示例
3) 后端：spring+spring mvc+mybatis+druid+shiro+dubbo+spring-data-redis+spring kafka,数据库是mysql
4) 前端：adminLTE+vue+jquery+bootstraps+layer

# 模块分类
## madara-common
* 一些共用工具类、枚举类等
* 共用实体类、dao、mapper文件等

## madara-web
* 一个类后台管理系统
* 采用shiro作权限控制
* 页面使用jsp，但几乎不使用jsp的特性，数据获取都是通过ajax，以便后续如果有静态化的需求，更容易过渡到前后端分离。
* 前端：adminLTE模板，vue+bootstraps+jquery，vue作为mvvm框架，前后端交互通过jquery完成，弹出层控件使用layer.js。

## madara-service
* 用来构建分布式业务系统，如交易系统等
* 集成dubbo作为RPC调用
* spring集成redis作为缓存
* spring集成kafka

## madara-facade
* dubbo的门面接口


#启动
1) clone或下载到本地目录，依赖于madara-common和madara-facade，先mvn install
2) 外部依赖于zookeeper、redis、myqsl，请确保已经提前装好，执行madara.sql
3) 修改dev-filter下的config.properties，替换成你的配置
4) 使用maven tomcat7插件启动: mvn tomcat7:run