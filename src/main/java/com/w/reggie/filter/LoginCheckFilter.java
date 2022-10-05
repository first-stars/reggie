package com.w.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.w.reggie.common.BaseContext;
import com.w.reggie.common.R;
import com.w.reggie.entity.Employee;
import com.w.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xin
 * @date 2022-07-11-8:32
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    @Autowired
    private EmployeeService employeeService;

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
        HttpServletResponse httpServletResponse=(HttpServletResponse) response;
//        log.info("拦截到请求：{}",httpServletRequest.getRequestURI());
//        chain.doFilter(httpServletRequest,httpServletResponse);

        //1 获取本次请求的uri
        String requestURI =httpServletRequest.getRequestURI();
        log.info("拦截到请求：{}",requestURI);
        //定义不需要处理的请求路径
        String[] urls=new String[]{
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



        boolean check = check(urls, requestURI);
        //3
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //4-1
        if (httpServletRequest.getSession().getAttribute("employee")!=null){
            log.info("后台用户已登录，id为{}",httpServletRequest.getSession().getAttribute("employee"));
            Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
//            long id = Thread.currentThread().getId();
//            log.info("线程id  {}",id);

            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        log.info("用户未登录");
        //4-2
        if (httpServletRequest.getSession().getAttribute("user")!=null){
            log.info("普通用户已登录，id为{}",httpServletRequest.getSession().getAttribute("user"));

            //        --------------------------------------------------------------------
            String url="/backend/**";
//            if(PATH_MATCHER.match(url, requestURI)){
//                log.info("非后台用户登录");
//                httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//                return;
//            }
//        --------------------------------------------------------------------
            Long userId = (Long) httpServletRequest.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
//            long id = Thread.currentThread().getId();
//            log.info("线程id  {}",id);

            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }


        log.info("用户未登录");
        //5 如果未登录，则返回未登录结果，通过输出流的方式向客户端反应结果
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url:urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
