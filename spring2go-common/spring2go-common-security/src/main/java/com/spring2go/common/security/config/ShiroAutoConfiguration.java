package com.spring2go.common.security.config;

import com.spring2go.common.security.aspect.InnerAspect;
import com.spring2go.common.security.shiro.ShiroAuthorizeFilter;
import com.spring2go.common.security.shiro.ShiroAuthorizeRealm;
import com.spring2go.common.security.util.TokenUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebFilterConfiguration;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Map;

/**
 * @description: Shiro自动配置
 * @author: xiaobin
 * @date: 2021-05-13 14:35
 */
@Configuration
//这里复用shiro.enabled配置项，方便统一暂时屏蔽shiro
@ConditionalOnProperty(name = "shiro.enabled", matchIfMissing = true)
@ConditionalOnClass(ShiroProperties.class)
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroAutoConfiguration extends ShiroWebFilterConfiguration {

    @Bean
    public InnerAspect innerAspect() {
        return new InnerAspect();
    }

    @Bean
    public Realm realm(TokenUtils tokenUtils) {
        return new ShiroAuthorizeRealm(tokenUtils);
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setRememberMeManager(null);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-
         * StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    @Override
    protected ShiroFilterFactoryBean shiroFilterFactoryBean() {
        //采用父类的默认方法生成shiroFilterFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = super.shiroFilterFactoryBean();
        //获取shiroFilterFactoryBean里的Filters集合
        Map filters = shiroFilterFactoryBean.getFilters();
        //put进一个自己编写的过滤器，并命名
        filters.put("auth", new ShiroAuthorizeFilter());
        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(ShiroProperties shiroProperties) {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        //-- 可以匿名访问的url
        //actuator  TODO 存在安全漏洞
        chainDefinition.addPathDefinition("/actuator/**", "anon");
        //swagger
        chainDefinition.addPathDefinition("/", "anon");
        chainDefinition.addPathDefinition("/doc.html", "anon");
        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        chainDefinition.addPathDefinition("/swagger**/**", "anon");
        chainDefinition.addPathDefinition("/webjars/**", "anon");
        chainDefinition.addPathDefinition("/v2/**", "anon");

        //每个服务自定义匿名访问路径游每个服务的配置文件单独配置
        chainDefinition.addPathDefinitions(shiroProperties.getPathDefinitions());

        //-- 其余资源都需要认证
        chainDefinition.addPathDefinition("/**", "auth");
        return chainDefinition;
    }

    /**
     * 下面的代码是添加注解支持
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        /**
         * 解决重复代理问题 github#994
         * 添加前缀判断 不匹配 任何Advisor
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        defaultAdvisorAutoProxyCreator.setAdvisorBeanNamePrefix("_no_advisor");
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
