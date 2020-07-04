package com.pengcheng.service;

import com.pengcheng.bean.Permission;

import java.util.List;
import java.util.Set;

/**
 * @Author: xie
 * @Date: 2020/7/4 11:48
 */
public interface PermissionService {

    List<Permission> listPermissionsByRoleId(String roleId);
    Set<String> findPermissionUrlsByUserId(String userId);
}
