package com.bjowernode.CRM.workbench.web.controller;

import com.bjowernode.CRM.settings.domain.User;
import com.bjowernode.CRM.settings.service.UserService;
import com.bjowernode.CRM.settings.service.impl.UserServiceImpl;
import com.bjowernode.CRM.utils.DateTimeUtil;
import com.bjowernode.CRM.utils.PrintJson;
import com.bjowernode.CRM.utils.ServiceFactory;
import com.bjowernode.CRM.utils.UUIDUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        if ("/workbench/activity/getUserList.do".equals(path)) {
            //传对应的方法
            getUserList(request,response);
        } else if ("/workbench/activity/save.do".equals(path)) {
            save(request,response);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("模态窗口市场活动添加方法进入");
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter(" owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        //创建时间从当前系统时间获取
        String createTime = request.getParameter(DateTimeUtil.getSysTime());
        //创建人也从当前session域中获取
        String createBy = request.getParameter(((User)request.getSession().getAttribute("user")).getName());
        String editTime = request.getParameter("editTime");
        String editBy = request.getParameter("editBy");
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response){
        System.out.println("获取用户列表方法进入");

        //这属于用户的业务，用代理，传入张三取出李四,Dao层查询操作在用户User那里
        //com.bjowernode.CRM.settings.service.impl.UserServiceImpl就是这个包
        //因为查询的是用户信息，所以还是用户的业务
        UserService user = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> list = user.getUserList();

        //解析成对应的Json格式,数组转换成Json的形式,方便向前端传值
        PrintJson.printJsonObj(response, list);

    }
}
