package com.extend.demo.domain;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

@Data
public class UserLogin extends User  implements Serializable {

    private String token;

    public static void main(String[] args) {
        Jedis jedis=new Jedis();
    }
}
