package com.extend.demo.cache;

import com.extend.demo.common.utils.RedisUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@PropertySource("classpath:/redis.properties")
@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.timeout}")
    private int timeOut;

    @Value("${redis.maxIdle}")
    private int maxIdle;

    @Value("${redis.maxTotal}")
    private int maxTotal;

    @Value("${redis.maxWaitMillis}")
    private int maxWaitMillis;

    @Value("${redis.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${redis.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;

    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.testWhileIdle}")
    private boolean testWhileIdle;


    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration  redisStandaloneConfiguration=new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        JedisClientConfiguration.JedisClientConfigurationBuilder builder=JedisClientConfiguration.builder();
        builder.connectTimeout(Duration.ofMillis(timeOut));
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory(redisStandaloneConfiguration,builder.build());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate=new RedisTemplate();
        initRedisTemplate(redisTemplate,redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisUtils redisUtils(RedisTemplate redisTemplate){
        return new RedisUtils(redisTemplate);
    }

    public void initRedisTemplate(RedisTemplate redisTemplate,RedisConnectionFactory factory){

        Jackson2JsonRedisSerializer<Object> serializer=new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper mapper=new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setEnableTransactionSupport(true);
        //redisTemplate.afterPropertiesSet();
        redisTemplate.setConnectionFactory(factory);
    }

}
