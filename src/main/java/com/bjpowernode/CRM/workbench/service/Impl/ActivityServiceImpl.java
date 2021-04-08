package com.bjpowernode.CRM.workbench.service.Impl;

import com.bjpowernode.CRM.utils.SqlSessionUtil;
import com.bjpowernode.CRM.vo.PaginationVO;
import com.bjpowernode.CRM.workbench.dao.ActivityDao;
import com.bjpowernode.CRM.workbench.domain.Activity;
import com.bjpowernode.CRM.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    //ActivityService接口实现类
    //new Dao层代理对象
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


    //查询客户分页操作
    @Override
    public PaginationVO pageList(Map<String, Object> map) {
        //取得total
        int total = activityDao.getTotalByCondition(map);
        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        //创建一个vo对象，将total和dataList封装到vo中
        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //将vo返回
        return vo;
    }

    //添加客户操作
    @Override
    public boolean save(Activity activity) {
        boolean flag = true;
        System.out.println(activity.getOwner()+"================");
        int count = activityDao.save(activity);
        if (count!=1){
            flag = false;
        }
        return flag;
    }
}
