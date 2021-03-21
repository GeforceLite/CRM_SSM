package com.bjowernode.CRM.settings.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet {
    //模板模式，Servlet会很多，不可能每一个业务都得去创建Servlet，所以用到了模板模式
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入用户控制器");
        //根据路径判断用户需求,path拿到的是web.xml里的url-partten：/settings/user/xxx.do
        String path = request.getServletPath();
        //进行判断
        //注意，setting路径前有/，非常容易出错
        if ("/settings/user/xxx.do".equals(path)) {
            //传对应的方法
            //xxx.(request,response)
        } else if ("/settings/user/xxx.do".equals(path)) {

        }
    }
}
