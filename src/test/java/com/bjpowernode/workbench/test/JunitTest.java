package com.bjpowernode.workbench.test;

import com.bjpowernode.CRM.utils.ServiceFactory;
import com.bjpowernode.CRM.utils.UUIDUtil;
import com.bjpowernode.CRM.workbench.domain.Activity;
import com.bjpowernode.CRM.workbench.service.ActivityService;
import com.bjpowernode.CRM.workbench.service.Impl.ActivityServiceImpl;
import com.bjpowernode.CRM.workbench.service.Impl.ClueServiceImpl;
import org.junit.Test;

public class JunitTest {
    //Junit单元测试以多线程方式进行测试，主要是测试业务层逻辑，控制器和Dao层一般来说是不用测的
    @Test
    public void test(){
        ClueServiceImpl clueService = new ClueServiceImpl();
        clueService.convert("464072f8e0234409981c8940c9d6a506", null, "40f6cdea0bd34aceb77492a1656d9fb3");
    }
}
