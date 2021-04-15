package com.bjpowernode.CRM.workbench.web.controller;

import com.bjpowernode.CRM.settings.domain.User;
import com.bjpowernode.CRM.settings.service.UserService;
import com.bjpowernode.CRM.settings.service.impl.UserServiceImpl;
import com.bjpowernode.CRM.utils.DateTimeUtil;
import com.bjpowernode.CRM.utils.PrintJson;
import com.bjpowernode.CRM.utils.ServiceFactory;
import com.bjpowernode.CRM.utils.UUIDUtil;
import com.bjpowernode.CRM.vo.PaginationVO;
import com.bjpowernode.CRM.workbench.domain.Activity;
import com.bjpowernode.CRM.workbench.domain.ActivityRemark;
import com.bjpowernode.CRM.workbench.service.ActivityService;
import com.bjpowernode.CRM.workbench.service.Impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//这里是控制器，日后由Spring负责编写
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入线索控制器");
        //根据路径判断用户需求,path拿到的是web.xml里的url-partten：/workbench/clue/xxx.do
        String path = request.getServletPath();
        //注意，setting路径前有/，非常容易出错
        //模板模式，Servlet会很多，不可能每一个业务都得去创建Servlet，所以用到了模板模式
        if ("/workbench/clue/xxx.do".equals(path)) {

        }
    }
    }
