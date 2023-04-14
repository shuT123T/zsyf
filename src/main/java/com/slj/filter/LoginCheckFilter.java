package com.slj.filter;

import com.alibaba.fastjson.JSON;
import com.slj.common.BaseContext;
import com.slj.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Author shu
 * @Create 2022-10-28
 */
@Configuration
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        1、获取请求URI
        String requestURI = request.getRequestURI();
        long id = Thread.currentThread().getId();
//        log.info("拦截到的请求：{}",requestURI);
        // 定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
        //2、判断本次请求是否进行处理
        boolean check = check(urls, requestURI);

        //3，如果不需要处理，则直接放行
        if (check) {
            filterChain.doFilter(request, response);
            return;
        }
        // 4-1，确定登录状态，如果管理员已经登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("管理员登录");
            Long empId = (Long) request.getSession().getAttribute(("employee"));
//            System.out.println("empId = " + empId);
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request, response);
            return;
        }
        // 4-2，确定登录状态，如果用户已经登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户登录");
            Long userId = (Long) request.getSession().getAttribute(("user"));
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }
        log.info("未登录");
        // 5，如果未登录则返回不登录
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match)
                return true;
        }
        return false;
    }
}
