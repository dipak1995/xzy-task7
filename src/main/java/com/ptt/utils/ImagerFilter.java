package com.ptt.utils;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImagerFilter implements Filter {

        @Override
        public void destroy() {
            System.out.println("销毁");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            System.out.println("dofilter");
            //转换成HttpServlet对象
            HttpServletRequest httprequest=(HttpServletRequest)request;
            HttpServletResponse httpresponse=(HttpServletResponse)response;

            //获取上一个地址
            String referer = httprequest.getHeader("Referer");

            String serverName = httprequest.getServerName();

            System.out.println(referer+"\t"+serverName);
            //如果地址为空,则有可能是直接访问资源
            //地址不正确,则是非法访问请求
            if(referer==null||!referer.contains(serverName)) {
                //转发到非法提示
                request.getRequestDispatcher("/image/aa.jpg").forward(request, response);
                return;
            }

            //资源放行
            chain.doFilter(request, response);

        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
            System.out.println("初始化");
        }


}
