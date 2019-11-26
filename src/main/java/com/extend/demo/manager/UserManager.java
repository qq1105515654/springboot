package com.extend.demo.manager;

import com.extend.demo.domain.User;
import com.extend.demo.domain.UserLogin;

import java.io.IOException;

public interface UserManager {

    public UserLogin queryUserByUserNameAndPassword(User user, String accessToken);

}
