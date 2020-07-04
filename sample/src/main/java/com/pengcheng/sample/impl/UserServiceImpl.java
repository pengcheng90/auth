package com.pengcheng.sample.impl;

import com.pengcheng.bean.User;
import com.pengcheng.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: xie
 * @Date: 2020/7/4 14:55
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findByUserName(String userName) {
        User user = new User();
        user.setId("123");
        user.setName("123");
        // 密码的加密方式要和realm 中配置一致
        user.setPassword("4280d89a5a03f812751f504cc10ee8a5");
        return user;
    }

}
