package com.extend.demo.manager.impl;

import com.extend.demo.common.utils.TokenGenerateUtils;
import com.extend.demo.dao.UserDao;
import com.extend.demo.domain.User;
import com.extend.demo.domain.UserLogin;
import com.extend.demo.manager.UserManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDao userDao;

    @Autowired
    private Jedis jedis;


    @Override
    public UserLogin queryUserByUserNameAndPassword(User user, String accessToken) {
       /* jedis.connect();

        User findUser;
        //如果Redis缓存中没有这个用户的信息从数据库中取
        if(userLogin==null){
            findUser=userDao.queryUserByInfo(user);
            if(findUser==null){
                return null;
            }
            userLogin=new UserLogin();
            userLogin.setUserId(findUser.getUserId());
            userLogin.setPassword(findUser.getPassword());
            userLogin.setUserName(findUser.getUserName());
            userLogin.setToken(TokenGenerateUtils.generateToken(findUser.getUserName()));
            //将该用户信息存入到缓存中 并且存储 2个小时
            redisTemplate.opsForValue().set(findUser.getUserName(),userLogin,2, TimeUnit.HOURS);
            return userLogin;
        }else{
            //如果用户传入的 token 与缓存中的不一致则表示用户登录已经过期，或者没有登陆过
            if(!userLogin.getToken().equals(accessToken)){
                return null;
            }
        }
        return userLogin;*/
        return null;
    }

}
