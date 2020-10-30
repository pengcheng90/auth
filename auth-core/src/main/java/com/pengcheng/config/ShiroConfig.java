package com.pengcheng.config;

import com.pengcheng.URLPathMatchingFilter;
import com.pengcheng.realm.DefaultRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengcheng
 * @date 2020/7/04  15:23
 */
@Configuration
public class ShiroConfig {

    @Autowired
    DefaultWebSessionManager sessionManager;
    @Autowired
    CacheManager cacheManager;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        //访问权限配置
        filtersMap.put("requestURL", getURLPathMatchingFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        List<String> urls = Collections.emptyList();
        for (String url : urls) {
            filterChainDefinitionMap.put(url, "anon");
        }
        filterChainDefinitionMap.put("/admin", "requestURL");
        filterChainDefinitionMap.put("/admin/**", "requestURL");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        return shiroFilterFactoryBean;
    }

    @Bean
    @DependsOn("shiroRedisConfig")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(cacheManager);
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    /**
     * 需要密码登录的realm
     *
     * @return MyShiroRealm
     */
    @Bean
    public DefaultRealm myRealm() {
        DefaultRealm myRealm = new DefaultRealm();
//        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }

    /**
     * 凭证匹配器
     * <p>
     * 加密算法：md5加盐加密10次
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密算法的名称
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //配置加密的次数
        hashedCredentialsMatcher.setHashIterations(2);
        //是否存储为16进制
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 访问 权限 拦截器
     *
     * @return
     */
    public URLPathMatchingFilter getURLPathMatchingFilter() {
        return new URLPathMatchingFilter();
    }

}