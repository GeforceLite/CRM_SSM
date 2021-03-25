package com.bjowernode.CRM.workbench.web.controller;

import com.bjowernode.CRM.settings.domain.User;
import com.bjowernode.CRM.settings.service.UserService;
import com.bjowernode.CRM.settings.service.UserServiceImpl;
import com.bjowernode.CRM.utils.MD5Util;
import com.bjowernode.CRM.utils.PrintJson;
import com.bjowernode.CRM.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//这里是控制器，日后由Spring负责编写
public class ActivityController extends HttpServlet {
    //模板模式，Servlet会很多，不可能每一个业务都得去创建Servlet，所以用到了模板模式
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入市场活动控制器");
        //根据路径判断用户需求,path拿到的是web.xml里的url-partten：/workbench/activity/xxx.do
        String path = request.getServletPath();
        //进行判断
        //注意，setting路径前有/，非常容易出错
        if ("/workbench/activity/xxx.do".equals(path)) {
            //传对应的方法
            //xxx.(request,response)
        } else if ("/workbench/activity/xxx.do".equals(path)) {

        }
    }
}
