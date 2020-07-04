package com.pengcheng.service;

import com.pengcheng.bean.Role;

import java.util.List;

/**
 * @Author: xie
 * @Date: 2020/7/4 11:47
 */
public interface RoleService {

    List<Role> listRolesByUserId(String userId);
}
