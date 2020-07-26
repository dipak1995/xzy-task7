package com.ptt.utils;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyInterceptor implements HandlerInterceptor {

    private static Logger logger = Logger.getLogger(MyInterceptor.class);

    //在请求处理的方法之前执行
    //如果返回true执行下一个拦截器
    //如果返回false就不执行下一个拦截器
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException, IOException {
        // 如果是首页面则放行
//        System.out.println("uri: " + request.getRequestURI());
//        if (request.getRequestURI().contains("main")) {
//            return true;
//        }


        // 获取cookie集合
        Cookie[] cookies = request.getCookies();
        logger.info("Cookie长度为: " + cookies.length);
        logger.info("拦截器获取到的Cookie: " + String.valueOf(cookies));

        if (cookies != null) {
            logger.info("开始遍历11");
            // 遍历
            for (Cookie cookie : cookies) {
                logger.info("当前cookie的值: " + cookie.getValue() + " 名字为:" + cookie.getName());
                // 判断是否有token
                if (cookie.getName().equals("token")) {
                    String tokenDES = cookie.getValue();
                    logger.info("====加密的tokenDES: " + tokenDES);

                    String token = DESUtil.getDecryptString(tokenDES);//解密
                    logger.info("=====token的解密value:" + token);


                    // 分割字符串 获取id
                    Integer id = Integer.valueOf(token.split("=")[0]);
                    logger.info("=====id为: "+id);
                    return true;
                }
            }
        }logger.info("=====cookie为空=====");
            response.sendRedirect(request.getContextPath() + "/tologin");
            return false;
        }


//        System.out.println("uri: " + request.getRequestURI());
//        if (request.getRequestURI().contains("allBmb")) {
//            return true;
//        }
//
//        // 如果是登陆页面则放行
//        System.out.println("uri: " + request.getRequestURI());
//        if (request.getRequestURI().contains("login")) {
//
//            System.out.println("登录成功");
//            return true;
//        }


        // 如果是注册页面则放行
//        System.out.println("uri: " + request.getRequestURI());
//        if (request.getRequestURI().contains("register")) {
//            return true;
//        }
//        HttpSession session = .getSession();
//
//        // 如果用户已登陆也放行
//        if (session.getAttribute("user") != null) {
//
//            System.out.println("======已登录====");
//            return true;
//        }
//
//        // 用户没有登陆跳转到登陆页面
//        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
//        return false;
//    }
//
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
}