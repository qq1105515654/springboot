package com.extend.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.extend.demo.cache.RedisConstants;
import com.extend.demo.common.utils.RedisUtils;
import com.extend.demo.domain.User;
import com.extend.demo.domain.UserLogin;
import com.extend.demo.manager.UserManager;
import com.extend.demo.message.HelloMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

@Controller
public class UserLoginController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private HelloMessage message;

    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        message.fanoutSend();
        message.sendHello();
        message.topicASend();
        message.topicBSend();
        message.topicCSend();
        return "index";
    }

    @ResponseBody
    @RequestMapping("/login")
    public Object login(User user, HttpServletRequest request){

        try{
           // redisUtils.set(user.getUserName(),user, RedisConstants.database1);
            User user1= (User) redisUtils.get(user.getUserName(),RedisConstants.database1);

            System.out.println(user1);
            return "缓存设置成功";
        }catch (Exception e){
            e.printStackTrace();
            return "缓存设置失败";
        }


       /* HttpSession session=request.getSession();
        String accessToken= (String) session.getAttribute("access_token");
        UserLogin userLogin=userManager.queryUserByUserNameAndPassword(user,accessToken);
        session.setAttribute("access_token",userLogin.getToken());
        if(userLogin==null){
            return "请重新登录!";
        }
        return "登录成功!";*/
    }

}
