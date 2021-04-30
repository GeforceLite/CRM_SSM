package com.bjpowernode.CRM.workbench.web.controller;

import com.bjpowernode.CRM.settings.domain.User;
import com.bjpowernode.CRM.settings.service.UserService;
import com.bjpowernode.CRM.settings.service.impl.UserServiceImpl;
import com.bjpowernode.CRM.utils.DateTimeUtil;
import com.bjpowernode.CRM.utils.PrintJson;
import com.bjpowernode.CRM.utils.ServiceFactory;
import com.bjpowernode.CRM.utils.UUIDUtil;
import com.bjpowernode.CRM.workbench.dao.CustomerDao;
import com.bjpowernode.CRM.workbench.domain.Tran;
import com.bjpowernode.CRM.workbench.domain.TranHistory;
import com.bjpowernode.CRM.workbench.service.CustomerService;
import com.bjpowernode.CRM.workbench.service.Impl.CustomerServiceImpl;
import com.bjpowernode.CRM.workbench.service.Impl.TranServiceImpl;
import com.bjpowernode.CRM.workbench.service.TranService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//这里是控制器，日后由Spring负责编写
public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入线索控制器");
        //根据路径判断用户需求,path拿到的是web.xml里的url-partten：/workbench/clue/xxx.do
        String path = request.getServletPath();
        //注意，setting路径前有/，非常容易出错
        //模板模式，Servlet会很多，不可能每一个业务都得去创建Servlet，所以用到了模板模式
        if ("/workbench/transaction/add.do".equals(path)) {
            add(request, response);
        } else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            getCustomerName(request, response);
        } else if ("/workbench/transaction/save.do".equals(path)) {
            save(request, response);
        } else if ("/workbench/transaction/detail.do".equals(path)) {
            detail(request, response);
        } else if ("/workbench/transaction/getHistoryListByTranId.do".equals(path)) {
            getHistoryListById(request, response);
        } else if ("/workbench/transaction/changeStage.do".equals(path)) {
            changeStage(request, response);
        }
    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行改变阶段操作");
        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();

        Tran tran = new Tran();
        tran.setId(id);
        tran.setStage(stage);
        tran.setMoney(money);
        tran.setExpectedDate(expectedDate);
        tran.setEditBy(editBy);
        tran.setEditTime(editTime);

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        Boolean flag = tranService.changeStage(tran);
        Map<String,String> pMap = (Map<String,String>)request.getAttribute("pMap");
        tran.setPossibility(pMap.get(stage));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success",flag);
        map.put("t", tran);
        PrintJson.printJsonObj(response,map);
    }

    private void getHistoryListById(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("通过Id查询交易历史");
        String tranId = request.getParameter("tranId");
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<TranHistory> list=tranService.getHistoryListById(tranId);
        //阶段和可能性之间的对应关系
        Map<String,String> pMap = (Map<String,String>)request.getAttribute("pMap");
        //遍历交易历史列表，为了取得历史阶段，用阶段拿可能性，拿完可能性放进tranHistory对象里一起打回前端
        for(TranHistory tranHistory:list){
            //根据每一条交易历史取出每一个阶段
            String stage = tranHistory.getStage();
            String possibility=pMap.get(stage);
            tranHistory.setPossibility(possibility);
        }
        PrintJson.printJsonObj(response,list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到详细信息页");
        String id = request.getParameter("id");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Tran t = ts.detail(id);
        //处理可能性
        /*

            阶段 t
            阶段和可能性之间的对应关系 pMap

         */
        String stage = t.getStage();
        Map<String,String> pMap = (Map<String,String>)this.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);
        request.setAttribute("t", t);
        //要用request域存值，就一定要转发,转发就能把页面停在detail.do上面
        //但是如果用了重定向，就会把页面送到detail.jsp上面，页面数据带不过去
        //如果数据有刷新的话显示不出来，所以就要用请求转发
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("交易创建进入");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName");//此处我们暂时只有客户名称，还没有id
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        Tran tran = new Tran();
        tran.setId(id);
        tran.setOwner(owner);
        tran.setMoney(money);
        tran.setName(name);
        tran.setExpectedDate(expectedDate);
        tran.setStage(stage);
        tran.setType(type);
        tran.setSource(source);
        tran.setActivityId(activityId);
        tran.setContactsId(contactsId);
        tran.setCreateTime(createTime);
        tran.setCreateBy(createBy);
        tran.setDescription(description);
        tran.setContactSummary(contactSummary);
        tran.setNextContactTime(nextContactTime);
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Boolean result=tranService.save(tran, customerName);
        System.out.println(result);
        if (result){
            //由于这次提交的不是Ajax，是正常的表单提交，因此得用转发重定向
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }

    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        //自动补全查询进入
        System.out.println("取得客户名称列表，按客户名进行模糊查询");
        String name = request.getParameter("name");
        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<String> sList=customerService.getCustomerName(name);
        PrintJson.printJsonObj(response,sList);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //进入到跳转交易添加页的操作
        System.out.println("进入到跳转交易添加页的操作");
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList=userService.getUserList();
        request.setAttribute("uList",userList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }
}
