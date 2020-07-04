package com.pengcheng.sample.impl;

import com.pengcheng.bean.Role;
import com.pengcheng.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xie
 * @Date: 2020/7/4 14:54
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public List<Role> listRolesByUserId(String userId) {
        return null;
    }
}
