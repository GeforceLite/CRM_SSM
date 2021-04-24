package com.bjpowernode.workbench.test;

import com.bjpowernode.CRM.utils.ServiceFactory;
import com.bjpowernode.CRM.workbench.service.CustomerService;
import com.bjpowernode.CRM.workbench.service.Impl.CustomerServiceImpl;
import com.bjpowernode.CRM.workbench.web.controller.TranController;
import org.junit.Test;

import java.util.List;

public class JunitTest {
    //Junit单元测试以多线程方式进行测试，主要是测试业务层逻辑，控制器和Dao层一般来说是不用测的
    @Test
    public void test(){
        /*ClueServiceImpl clueService = new ClueServiceImpl();
        clueService.convert("464072f8e0234409981c8940c9d6a506", null, "40f6cdea0bd34aceb77492a1656d9fb3");*/
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        List list=customerService.getCustomerName("阿");
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
