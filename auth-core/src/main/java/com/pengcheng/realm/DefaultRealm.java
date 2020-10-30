package com.pengcheng.realm;

import com.pengcheng.bean.Permission;
import com.pengcheng.bean.Role;
import com.pengcheng.bean.User;
import com.pengcheng.service.PermissionService;
import com.pengcheng.service.RoleService;
import com.pengcheng.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 默认realm
 *
 * @Author: pengcheng
 * @Date: 2020/7/4 11:45
 */
public class DefaultRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        List<Role> roles = roleService.listRolesByUserId(user.getId());
        for (Role role : roles) {
            authorizationInfo.addRole(role.getName());
            List<Permission> permissions = permissionService.listPermissionsByRoleId(role.getId());
            for (Permission p : permissions) {
                authorizationInfo.addStringPermission(p.getName());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = ((UsernamePasswordToken) token).getUsername();
        User user = userService.findByUserName(userName);
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                "defaultRealm"
        );
        return simpleAuthenticationInfo;
    }
}
