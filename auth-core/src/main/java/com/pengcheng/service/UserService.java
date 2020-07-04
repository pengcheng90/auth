package com.pengcheng.service;

import com.pengcheng.bean.User;

/**
 * @Author: pengcheng
 * @Date: 2020/7/4 11:47
 */
public interface UserService {

    User findByUserName(String userName);

}
