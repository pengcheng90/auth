package com.pengcheng.sample.impl;

import com.pengcheng.bean.Permission;
import com.pengcheng.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Author: xie
 * @Date: 2020/7/4 14:54
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public List<Permission> listPermissionsByRoleId(String roleId) {
        return null;
    }

    @Override
    public Set<String> findPermissionUrlsByUserId(String userId) {
        return null;
    }
}
