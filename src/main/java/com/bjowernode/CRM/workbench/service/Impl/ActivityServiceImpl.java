package com.bjowernode.CRM.workbench.service.Impl;

import com.bjowernode.CRM.utils.SqlSessionUtil;
import com.bjowernode.CRM.workbench.dao.ActivityDao;
import com.bjowernode.CRM.workbench.domain.Activity;
import com.bjowernode.CRM.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    //ActivityService接口实现类
    //new Dao层代理对象
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


    @Override
    public boolean save(Activity activity) {
        boolean flag = true;
        int count = activityDao.save(activity);
        if (count!=1){
            flag = false;
        }
        return flag;
    }
}
