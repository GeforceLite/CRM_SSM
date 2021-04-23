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
import com.bjpowernode.CRM.workbench.domain.Clue;
import com.bjpowernode.CRM.workbench.domain.Tran;
import com.bjpowernode.CRM.workbench.service.ActivityService;
import com.bjpowernode.CRM.workbench.service.ClueService;
import com.bjpowernode.CRM.workbench.service.Impl.ActivityServiceImpl;
import com.bjpowernode.CRM.workbench.service.Impl.ClueServiceImpl;

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
        if ("/workbench/clue/getUserList.do".equals(path)) {
            getUserList(request, response);
        } else if ("/workbench/clue/save.do".equals(path)) {
            save(request, response);
        } else if ("/workbench/clue/detail.do".equals(path)) {
            detail(request, response);
        } else if ("/workbench/clue/getActivityListByClue.do".equals(path)) {
            getActivityListByClue(request, response);
        } else if ("/workbench/clue/unbund.do".equals(path)) {
            unbund(request, response);
        } else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)) {
            getActivityListByNameAndNotByClueId(request, response);
        } else if ("/workbench/clue/bund.do".equals(path)) {
            bund(request, response);
        } else if ("/workbench/clue/getActivityListByName.do".equals(path)) {
            getActivityListByName(request, response);
        } else if ("/workbench/clue/convert.do".equals(path)) {
            convert(request, response);
        }
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("线索转换进入");
        String clueId = request.getParameter("clueId");
        //接收标记信号，观察是否需有标记，若有则创建交易
        String flag = request.getParameter("flag");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();        Tran tran = null;
        if ("a".equals(flag)){
            //需要创建交易并且线索转换，去掉一条线索，添加一个客户和联系人
            //到这个位置，就代表出现了交易，可以创建一个交易对象来接收对应的数据源
            tran = new Tran();
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String createTime = DateTimeUtil.getSysTime();
            String id = UUIDUtil.getUUID();
            tran.setId(id);
            tran.setName(name);
            tran.setMoney(money);
            tran.setExpectedDate(expectedDate);
            tran.setStage(stage);
            tran.setActivityId(activityId);
            tran.setCreateBy(createBy);
            tran.setCreateTime(createTime);

        }
        //到这里如果tran对象仍然是一个空的对象，代表没有交易的产生
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Boolean flag1=clueService.convert(clueId,tran,createBy);
        //这里不是Ajax请求，所以就得用转发重定向了
        if (flag1){
            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
        }


    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("市场活动搜索进入");
        String aname = request.getParameter("aname");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> list=activityService.getActivityListByName(aname);
        PrintJson.printJsonObj(response,list);
    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("线索绑定");
        String cid = request.getParameter("cid");
        String aids[] = request.getParameterValues("aid");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Boolean flag=clueService.bund(cid,aids);
        PrintJson.printJsonObj(response,flag);
    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("关联市场活动模糊查询");
        String aname = request.getParameter("aname");
        String clueId = request.getParameter("clueId");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, String> map = new HashMap<>();
        map.put("aname", aname);
        map.put("clueId", clueId);
        List<Activity> aList = activityService.getActivityListByNameAndNotByClueId(map);
        PrintJson.printJsonObj(response,aList);
    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("解除关联操作");
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Boolean flag=clueService.unbund(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getActivityListByClue(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据线索id查询关联的市场活动列表");
        String id = request.getParameter("clueId");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List list=activityService.getActivityListByClue(id);
        PrintJson.printJsonObj(response,list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到线索的详细信息页");
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue=clueService.detail(id);
        request.setAttribute("c",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }


    //线索添加方法
    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("线索保存方法开始运行");
        Boolean result = true;
        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");
        Clue clue = new Clue();
        clue.setId(id);
        clue.setAddress(address);
        clue.setWebsite(website);
        clue.setState(state);
        clue.setSource(source);
        clue.setPhone(phone);
        clue.setOwner(owner);
        clue.setNextContactTime(nextContactTime);
        clue.setMphone(mphone);
        clue.setJob(job);
        clue.setFullname(fullname);
        clue.setEmail(email);
        clue.setDescription(description);
        clue.setCreateTime(createTime);
        clue.setCreateBy(createBy);
        clue.setContactSummary(contactSummary);
        clue.setCompany(company);
        clue.setAppellation(appellation);
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        result=clueService.save(clue);
        PrintJson.printJsonFlag(response,result);
    }


    //获取用户信息列表方法
    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("获取用户信息列表");
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List list=userService.getUserList();
        PrintJson.printJsonObj(response,list);
    }
}
