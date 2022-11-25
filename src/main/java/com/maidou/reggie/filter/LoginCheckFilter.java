package com.maidou.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.maidou.reggie.common.BaseContext;
import com.maidou.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/6 17:16
 *  登录拦截器
 **/
@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.接收请求
        String requestURI = request.getRequestURI();
        log.info("拦截到请求:{}",requestURI);
        //2.判断是不是要登录,登出或是静态资源的请求,放行
        if (checkURI(requestURI)) {
            log.info("请求:{}不需要处理,已放行",requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        //3-1判断是不是已经登录,已经登录,放行
        if (request.getSession().getAttribute("emp") != null) {
            Long id = (Long) request.getSession().getAttribute("emp");
            BaseContext.setCurrentId(id);
            log.info("请求:{}用户id{}已登录,已放行",requestURI,request.getSession().getAttribute("emp"));
            filterChain.doFilter(request, response);
            return;
        }
        //3-2判断是不是已经登录,已经登录,放行
        if (request.getSession().getAttribute("user") != null) {
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            log.info("请求:{}手机用户id{}已登录,已放行",requestURI,request.getSession().getAttribute("user"));
            filterChain.doFilter(request, response);
            return;
        }
        //4.未登录,返回未登录响应,让前端拦截器拦截
        log.info("请求:{}未登录,已拦截",requestURI);
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    public boolean checkURI(String requestURI) {
        String urls[] = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/login"
        };
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
