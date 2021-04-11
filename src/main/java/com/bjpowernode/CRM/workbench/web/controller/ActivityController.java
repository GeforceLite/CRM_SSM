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
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入市场活动控制器");
        //根据路径判断用户需求,path拿到的是web.xml里的url-partten：/workbench/activity/xxx.do
        String path = request.getServletPath();
        //注意，setting路径前有/，非常容易出错
        //模板模式，Servlet会很多，不可能每一个业务都得去创建Servlet，所以用到了模板模式
        if ("/workbench/activity/getUserList.do".equals(path)) {
            getUserList(request,response);
        }else if ("/workbench/activity/save.do".equals(path)) {
            save(request,response);
        }else if ("/workbench/activity/pageList.do".equals(path)) {
            pageList(request,response);
        }else if ("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }else if ("/workbench/activity/getUserListAndActivity.do".equals(path)){
            getUserListAndActivity(request,response);
        }else if ("/workbench/activity/update.do".equals(path)){
            update(request,response);
        }else if ("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/activity/getRemarkByAid.do".equals(path)){
            getRemarkByAid(request,response);
        }
    }

    private void getRemarkByAid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("市场活动备注信息查询方法");
        String activityId = request.getParameter("activityId");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> list=activityService.getRemarkByAid(activityId);
        PrintJson.printJsonObj(response,list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到市场活动详细信息页");
        String id = request.getParameter("id");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        //实际上就是根据id查单条然后请求转发,要把activity存到作用域中
        Activity activity = activityService.detail(id);
        request.setAttribute("a",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("更新市场活动方法进入");
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        //修改时间从当前系统时间获取
        String editTime = request.getParameter(DateTimeUtil.getSysTime());
        //修改人也从当前session域中获取
        String editBy = request.getParameter(((User)request.getSession().getAttribute("user")).getName());
        //vo对象，封装好参数传进去
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);
        activity.setCost(cost);
        activity.setDescription(description);
        //市场活动相关业务
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        //传入save方法
        boolean flag = activityService.update(activity);
        //返回给Ajax解析Json串
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询信息列表和根据市场活动id查询单条记录操作（编辑操作前期铺垫)");
        String id = request.getParameter("id");
        /*
            总结：
                controller调用service的方法，返回值应该是什么
                你得想一想前端要什么，就要从service层取什么
            前端需要的，问业务层去要
            uList
            a
            以上两项信息，复用率不高，我们选择使用map打包这两项信息即可
            map
         */
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String,Object> map=activityService.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("市场活动删除操作进入");
        //前端处理完的拼接请求串在这里就见不到了，因为浏览器自动就把&给去掉并且拆成5个串了
        //所以后端看不见&很正常
        String ids[] = request.getParameterValues("id");
        //拿到Activity代理对象
        ActivityService activtyService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=activtyService.delete(ids);
        PrintJson.printJsonFlag(response,flag);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("分页查询方法进入");
        String name = request.getParameter("name");
        String pageSizeStr = request.getParameter("pageSize");
        String pageNoStr = request.getParameter("pageNo");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
       //统计分页页数,对其进行转换
        int pageSize = Integer.valueOf(pageSizeStr);
        int pageNo = Integer.valueOf(pageNoStr);
        //计算跳过的页数
        int skipCount = (pageSize - 1) * pageNo;
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        //相当于Proxy来调用代理对象
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        /*
            前端要： 市场活动信息列表
                    查询的总条数

                    业务层拿到了以上两项信息之后，如果做返回
                    map
                    map.put("dataList":dataList)
                    map.put("total":total)
                    PrintJSON map --> json
                    {"total":100,"dataList":[{市场活动1},{2},{3}]}


                    vo
                    PaginationVO<T>
                        private int total;
                        private List<T> dataList;

                    PaginationVO<Activity> vo = new PaginationVO<>;
                    vo.setTotal(total);
                    vo.setDataList(dataList);
                    PrintJSON vo --> json
                    {"total":100,"dataList":[{市场活动1},{2},{3}]}

                    将来分页查询，每个模块都有，所以我们选择使用一个通用vo，操作起来比较方便

         */
        //这里是查询，查询结果返回后用vo封装一下json打回前端
        PaginationVO paginationVO=as.pageList(map);
        PrintJson.printJsonObj(response,paginationVO);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("模态窗口市场活动添加方法进入");
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        //创建时间从当前系统时间获取
        String createTime = request.getParameter(DateTimeUtil.getSysTime());
        //创建人也从当前session域中获取
        String createBy = request.getParameter(((User)request.getSession().getAttribute("user")).getName());
        //创建一个vo对象，封装好参数传进去
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);
        //市场活动相关业务
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        //传入save方法
        boolean flag = activityService.save(activity);
        //返回给Ajax解析Json串
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response){
        System.out.println("模态窗口获取用户下拉列表方法进入");

        //这属于用户的业务，用代理，传入张三取出李四,Dao层查询操作在用户User那里
        //com.bjowernode.CRM.settings.service.impl.UserServiceImpl就是这个包
        //因为查询的是用户信息，所以还是用户的业务
        UserService user = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> list = user.getUserList();

        //解析成对应的Json格式,数组转换成Json的形式,方便向前端传值
        PrintJson.printJsonObj(response, list);

    }


}
