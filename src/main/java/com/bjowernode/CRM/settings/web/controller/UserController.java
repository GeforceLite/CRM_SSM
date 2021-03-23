package com.bjowernode.CRM.settings.web.controller;

import com.bjowernode.CRM.settings.domain.User;
import com.bjowernode.CRM.settings.service.UserService;
import com.bjowernode.CRM.settings.service.UserServiceImpl;
import com.bjowernode.CRM.utils.MD5Util;
import com.bjowernode.CRM.utils.PrintJson;
import com.bjowernode.CRM.utils.ServiceFactory;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
//这里是控制器，日后由Spring负责编写
public class UserController extends HttpServlet {
    //模板模式，Servlet会很多，不可能每一个业务都得去创建Servlet，所以用到了模板模式
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入用户控制器");
        //根据路径判断用户需求,path拿到的是web.xml里的url-partten：/settings/user/xxx.do
        String path = request.getServletPath();
        //进行判断
        //注意，setting路径前有/，非常容易出错
        if ("/settings/user/login.do".equals(path)) {
            //传对应的方法
            //xxx.(request,response)
            login(request, response);
        } else if ("/settings/user/login.do".equals(path)) {

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入验证操作");
        String loginAct = request.getParameter("loginAct");
        System.out.println("loginAct="+loginAct);
        String loginPwd = request.getParameter("loginPwd");
        //这个位置拿完密码后，数据库密码有加密，所以需要处理一下转成MD5
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收ip地址getRemoteAddr()为获取ip地址的专有方法
        String ip = request.getRemoteAddr();
        System.out.println("-----ip="+ip);
        //未来业务层开发，统一用代理类形态的接口对象
        //创建service对象
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            //如果登陆失败了，直接跳到catch块了，不会跳到下一步的setAttribute里面
            User user = userService.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user",user);
            //如果登陆成功，返回一个json,全手动敲返回信息太费劲了，所以引入了工具类
            /*{"success":true}正常要拼一大串这样的引入工具后就会好写
            * PrintJson Class类直接调用方法传参拿结果就好，这里是登陆成功情况，如果不成功就
            * 跳到catch块里了，所以这里第二个参数填入的是true
            * */
            PrintJson.printJsonFlag(response,true);
        } catch (Exception e) {
            //业务层验证登陆失败，为controller发出异常报告
            e.printStackTrace();
            //用msg抓取错误信息
            String msg = e.getMessage();
            /*
            作为controller，需要为ajax提供更多信息
            无非就两种解决方法
                    1.把信息打包成为map，把map解析成json串
                    2.创建一个Vo对象，具体是什么可以百度，相当于把数据放到对象里
                    private boolean success
                    private String msg
                    PS:如果这种信息展现在未来还会大量的使用，我们创建一个Vo类反复使用
                    如果就业务里面用一次的话，那没有必要了，map传完解析成为json就好
                    这个msg就没必要了，map就好
            */
            Map<String, Object> map = new HashMap<String,Object>();
            //俩key，一个专门负责确认默认登陆错误情况的false，另外一个负责msg的内容
            map.put("success", false);
            map.put("msg", msg);
            //解析json串，并且发送给前端，PrintJson里面有自动传送的代码
            PrintJson.printJsonObj(response,map);
        }
    }
}
