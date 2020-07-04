package com.pengcheng.manager;

import com.pengcheng.bean.User;

/**
 * @Author: xie
 * @Date: 2020/7/4 11:55
 */
public interface TokenManager {

    User getByToken(String token);
}
