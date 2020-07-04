package com.pengcheng;

/**
 * @Author: xie
 * @Date: 2020/7/4 14:27
 */
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;
/**
 * URL拦截器
 * @author 言曌
 * @date 2019-10-12 17:56
 */
public class URLPathMatchingFilter extends PathMatchingFilter {
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //请求的url
        String requestURL = getPathWithinApplication(request);
        System.out.println("请求的url :" + requestURL);
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            // 如果没有登录, 跳到登录页面
            WebUtils.issueRedirect(request, response, "/admin/login");
            return false;
        }
        //从session里读取当前用户的权限URL列表
        Set<String> urls = (Set<String>) subject.getSession().getAttribute("permissionUrls");
        if (urls.contains(requestURL)) {
            return true;
        }
        //没有权限，跳到403页面
        WebUtils.issueRedirect(request, response, "/403");
        return false;
    }
}