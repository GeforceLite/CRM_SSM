package com.bjpowernode.CRM.web.filter;

import javax.servlet.*;
import java.io.IOException;

/*此处为编码过滤器，防止前端到后端的传输过程中，出现乱码
每次数据传输都从这里进行一次过滤，防止乱码问题的出现*/
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤字符集编码过滤器");
        //设置request对象的post请求字符集
        req.setCharacterEncoding("UTF-8");
        //设置response对象的响应字符集
        resp.setContentType("text/html;charset=utf-8");
        //过滤哪些文件在web.xml里面有设置，光过滤不行，还得放行
        filterChain.doFilter(req,resp);
    }
}
