package com.bjpowernode.CRM.workbench.web.listener;

import com.bjpowernode.CRM.settings.domain.DicValue;
import com.bjpowernode.CRM.settings.service.DicService;
import com.bjpowernode.CRM.settings.service.impl.DicServiceImpl;
import com.bjpowernode.CRM.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//监听器,数据字典用,监听到用户创建了上下文作用域对象,自动从数据库中查询数据字典放到cache（缓存）中
//来填充前端页面这样就可以大幅减少等待时间
public class SysInitListener implements ServletContextListener {
    //event是监听的对象，监听的是什么对象，就可以根据参数得到什么对象
    //这里就可以拿到全局作用域对象
    @Override
    public void contextInitialized(ServletContextEvent event) {
        //正常这个位置网页开启时就已经触发启动了
        System.out.println("服务器缓存处理数据字典开始");
        ServletContext application = event.getServletContext();
        //取数据字典，数据字典是根据codeType进行分类的，所以查出来的codeType一共是七种
        //所以会从业务层返回7个结果集（是被包裹成一个map的），以类型为map的key
        DicService dicService= (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map = dicService.getAll();
        Set<String> set=map.keySet();
        for (String key:set){
            //遍历，存数据字典
            application.setAttribute(key+"List",map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");
    }
 }
