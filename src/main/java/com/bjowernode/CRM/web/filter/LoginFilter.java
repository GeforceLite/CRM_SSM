package com.bjowernode.CRM.web.filter;

import com.bjowernode.CRM.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//过滤恶意登陆请求
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("进入过滤器验证session有无登陆");
        //需要拿到父类HttpServletRequest，ServletRequest方法太少，应付不了重定向等问题
        //父类转子类需要进行强制转换，子类转父类就不需要强制转换
        HttpServletRequest Request = (HttpServletRequest) request;
        HttpServletResponse Response = (HttpServletResponse) response;

        //拿到登陆的路径，放行，登录页面的ajax请求不拦截
        String GoPath = Request.getServletPath();
        //在拦截之前对其进行放行,一个是登录页的jsp文件，另一个是登录页对应的servlet文件
        //作正常请求判断和恶意请求判断
        if ("/login.jsp".equals(GoPath) || "/settings/user/login.do".equals(GoPath)) {
            //直接放行不进行拦截
            filterChain.doFilter(request,response);
        } else {
            /*否则的话就是恶意请求，该拦截的就拦截*/
            //拿到Session对象
            HttpSession httpSession=Request.getSession();
            //从session中拿到User对象
            User user = (User) httpSession.getAttribute("user");
            //如果有user对象即user对象不为空，那么就代表session有登陆过
            if (user != null) {
                //放行
                filterChain.doFilter(request,response);
            } else {
                //反之就是没登陆过，重定向到登录页
             /*为什么用重定向而不用转发?
                重定向的路径怎么写？
                在实际项目开发中，对于路径的使用，不论操作的是前端还是后端，应该一律使用绝对路径
                 关于转发和重定向的路径的写法如下：
                 转发：
                    使用的是一种特殊的绝对路径的使用方式，这种绝对路径前面不加/项目名，这种路径也称之为内部路径
                    /login.jsp
                 重定向：
                    使用的是传统绝对路径的写法，前面必须以/项目名开头，后面跟具体的资源路径
                    /crm/login.jsp

                为什么使用重定向，使用转发不行吗？
                   转发之后，路径会停留在老路径上，而不是跳转之后最新资源的路径
                   我们应该在为用户跳转到登录页的同时，将浏览器的地址栏应该自动设置为当前的登录页的路径
                   Request.getContextPath()+"Login.jsp"，获取路径
             */
                Response.sendRedirect(Request.getContextPath()+"/login.jsp");
            }
        }
    }
}
