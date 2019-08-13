package com.extend.demo.cache;

import com.extend.demo.domain.UserLogin;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.Jedis;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {


    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String passphrase;


    /*public CacheManager cacheManager(RedisTemplate redisTemplate){
        RedisCacheManager rcm=new RedisCacheManager(redisTemplate);
        return rcm;
    }*/

    @Bean
    public Jedis jedis(){
        Jedis jedis=new Jedis(host,port);
        jedis.auth(passphrase);
        return jedis;
    }

    /**
     * redisTemplate 相关配置
     * @param
     * @return
     */
    /*@Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate=new RedisTemplate();
        //配置连接工厂
        redisTemplate.setConnectionFactory(factory);
        //使用Jackson2JsonRedisSerializer 来序列化和反序列化 redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer<Object> serializer=new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper mapper=new ObjectMapper();
        //指定要序列化，field,get和set,以及修饰符范围，ANY是都有包括，private和public
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //指定序列化输入的类型，类必须是非 final 修饰的，final修饰的类，比如String,Integer等会抛出异常。
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        //key 采用StringRedisSerializer来序列化和反序列化 redis的 key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //使用 Json序列化
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }*/

    @Bean
    public HashOperations<String,String,Object> hashOperations(RedisTemplate redisTemplate){
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String,Object> valueOperations(RedisTemplate redisTemplate){
        return redisTemplate.opsForValue();
    }

    @Bean
    public SetOperations<String,Object> setOperations(RedisTemplate redisTemplate){
        return redisTemplate.opsForSet();
    }

    @Bean
    public ListOperations<String,Object> listOperations(RedisTemplate redisTemplate){
        return redisTemplate.opsForList();
    }


}
