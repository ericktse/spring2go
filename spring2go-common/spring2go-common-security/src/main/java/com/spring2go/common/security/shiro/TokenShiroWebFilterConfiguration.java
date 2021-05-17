//package com.spring2go.common.security.shiro;
//
//import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebFilterConfiguration;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Map;
//
///**
// * @description: TODO
// * @author: xiaobin
// * @date: 2021-05-14 13:40
// */
//@Configuration
//public class TokenShiroWebFilterConfiguration extends ShiroWebFilterConfiguration {
//    @Override
//    protected ShiroFilterFactoryBean shiroFilterFactoryBean() {
//        //TODO:直接使用shiroAutoConfiguration来继承ShiroWebFilterConfiguration 的话，会出现循环依赖的bug而导致无法启动。
//
//        //采用父类的默认方法生成shiroFilterFactoryBean
//        ShiroFilterFactoryBean shiroFilterFactoryBean = super.shiroFilterFactoryBean();
//        //获取shiroFilterFactoryBean里的Filters集合
//        Map filters = shiroFilterFactoryBean.getFilters();
//        //put进一个自己编写的过滤器，并命名，上面会引用到
//        filters.put("auth", new ShiroAuthorizeFilter());
//        shiroFilterFactoryBean.setFilters(filters);
//
//        return shiroFilterFactoryBean;
//    }
//}
