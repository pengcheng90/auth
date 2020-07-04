package com.pengcheng.sample.controller;

import com.pengcheng.bean.User;
import com.pengcheng.sample.bean.ResultBean;
import com.pengcheng.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


/**
 * @Author: pengcheng
 * @Date: 2020/7/4 14:41
 */
@RestController()
@RequestMapping("login")
public class LoginController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 验证登录信息
     *
     * @param account  用户名
     * @param password password 密码
     * @return JsonResult JsonResult
     */
    @PostMapping(value = "/getLogin")
    @ResponseBody
    public ResultBean getLogin(String account, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("123", "4280d89a5a03f812751f504cc10ee8a5");
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                User user = (User) subject.getPrincipal();
                // 将用户的权限URL列表放到 session 中
                Set<String> permissionUrls = permissionService.findPermissionUrlsByUserId(user.getId());
                subject.getSession().setAttribute("permissionUrls", permissionUrls);
                return new ResultBean("200", "登录成功");
            }
        } /*catch (UnknownAccountException e) {
//            log.info("UnknownAccountException -- > 账号不存在：");
            return new ResultBean("500", "账号不存在");
        } catch (IncorrectCredentialsException e) {
            return new ResultBean("500", "密码错误");
        } catch (LockedAccountException e) {
//            log.info("LockedAccountException -- > 账号被锁定");
            return new ResultBean("500", "账号被锁定");
        }*/ catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResultBean("500", "服务器内部错误");
    }

    /**
     * 退出登录
     *
     * @return 重定向到/admin/login
     */
    @GetMapping(value = "/logOut")
    public String logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/admin/login";
    }

}
