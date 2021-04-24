package com.bjpowernode.CRM.workbench.web.listener;

import com.bjpowernode.CRM.settings.domain.DicValue;
import com.bjpowernode.CRM.settings.service.DicService;
import com.bjpowernode.CRM.settings.service.impl.DicServiceImpl;
import com.bjpowernode.CRM.utils.ServiceFactory;

import javax.el.ResourceBundleELResolver;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

//监听器,数据字典用,监听到用户创建了上下文作用域对象,自动从数据库中查询数据字典放到cache（缓存）中
//来填充前端页面这样就可以大幅减少等待时间
public class SysInitListener implements ServletContextListener {
    //event是监听的对象，监听的是什么对象，就可以根据参数得到什么对象
    //这里就可以拿到全局作用域对象
    @Override
    public void contextInitialized(ServletContextEvent event) {
        //正常这个位置网页开启时就已经触发启动了

        //System.out.println("上下文域对象创建了");

        System.out.println("服务器缓存处理数据字典开始");

        ServletContext application = event.getServletContext();

        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());
        /*
            应该管业务层要
            7个list

            可以打包成为一个map
            业务层应该是这样来保存数据的：
                map.put("appellationList",dvList1);
                map.put("clueStateList",dvList2);
                map.put("stageList",dvList3);
                ....
                ...
         */
        Map<String, List<DicValue>> map = ds.getAll();
        //将map解析为上下文域对象中保存的键值对
        Set<String> set = map.keySet();
        for(String key:set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");



//----------------------------------------------------------------------------
        //数据字典处理好之后，就处理Stage2Possibility.properties文件读取到服务器缓存里面
        /*处理方法，把文件中的键值对关系转化成为Java中的键值对关系即为Map
        Map<String(阶段stage),String(可能性大小)> pMap
        pMap.put("01资质审查"，10)
        pMap.put("02需求分析",25)
        ....
        填满Map送回服务器缓存中
        application.setAttribute("pMap",pMap)
        */
        //解析properties文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> enumeration=resourceBundle.getKeys();
        Map<String, String> pMap = new HashMap<>();
        while (enumeration.hasMoreElements()){
            //阶段
            String key = enumeration.nextElement();
            //可能性
            String value=resourceBundle.getString(key);
            pMap.put(key, value);
        }
        application.setAttribute("pMap",pMap);
    }
 }
