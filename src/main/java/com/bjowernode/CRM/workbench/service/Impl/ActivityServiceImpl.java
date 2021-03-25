package com.bjowernode.CRM.workbench.service.Impl;

import com.bjowernode.CRM.utils.SqlSessionUtil;
import com.bjowernode.CRM.workbench.dao.ActivityDao;
import com.bjowernode.CRM.workbench.service.ActivityService;
import org.apache.ibatis.session.SqlSessionFactory;

public class ActivityServiceImpl implements ActivityService {
    //ActivityService接口实现类
    //new Dao层对象
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

}
