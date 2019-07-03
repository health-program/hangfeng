package com.paladin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc//启动MVC
@EnableTransactionManagement // 启注解事务管理
@SpringBootApplication//SpringBoot启动核心
@EnableScheduling
public class TomcatApplication extends SpringBootServletInitializer  {

    /**
     * 如果要发布到自己的Tomcat中的时候，需要继承SpringBootServletInitializer类，并且增加如下的configure方法。
     * 如果不发布到自己的Tomcat中的时候，就无需上述的步骤
     */
   @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TomcatApplication.class);
    }

    public static void main(String[] args){
        SpringApplication.run(TomcatApplication.class, args);
    }

}
