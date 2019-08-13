package com.extend.demo.dao;

import com.extend.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao {

    @Select("select * from user where user_name=#{user.userName} and password=#{user.password}")
    User queryUserByInfo(@Param("user") User user);

}
