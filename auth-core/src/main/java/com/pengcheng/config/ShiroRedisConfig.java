package com.pengcheng.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroRedisConfig {
    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.database}")
    private int database;

    @Value("${redis.timeout}")
    private int timeout;


    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);// 主机地址
        redisManager.setPort(port);// 端口
        redisManager.setPassword(password);// 访问密码
        redisManager.setDatabase(database);// 默认数据库
        redisManager.setTimeout(timeout);// 过期时间
        return redisManager;
    }

    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件 MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO
     * 提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     *
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDao = new RedisSessionDAO();
        redisSessionDao.setKeyPrefix("shiro-session");//配置session前缀
//        redisSessionDao.setSessionIdGenerator((org.apache.shiro.session.mgt.eis.SessionIdGenerator) sessionIdGenerator());
        redisSessionDao.setRedisManager(redisManager());
        // session在redis中的保存时间,最好大于session会话超时时间
        redisSessionDao.setExpire(timeout);
        return redisSessionDao;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager redisCacheManager = new DefaultWebSessionManager();
        redisCacheManager.setSessionDAO(redisSessionDAO());
        return redisCacheManager;
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

//    @Bean
//    public SessionIdGenerator sessionIdGenerator() {
//        return (SessionIdGenerator) new JavaUuidSessionIdGenerator();
//    }

}
