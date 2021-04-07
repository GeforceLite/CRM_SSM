package com.bjpowernode.CRM.workbench.service;

import com.bjpowernode.CRM.vo.PaginationVO;
import com.bjpowernode.CRM.workbench.domain.Activity;

import java.util.Map;

//市场活动服务接口
public interface ActivityService {

    boolean save(Activity activity);

    PaginationVO pageList(Map<String, Object> map);
}
