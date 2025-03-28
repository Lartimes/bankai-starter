module bankai.core {
    requires static lombok;
    requires org.slf4j;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.cloud.commons;
    requires spring.context;
    requires spring.core;
    requires spring.cloud.context;
}
/*
* 启动事件监听类
* springUtil applicationContext
* 默认加载环境类
* =================
* todo 步骤拦截器
* 加载bean环境 分类器
* 扩展bean /service/mapper/controller/ ExtendBean ....
*
* */