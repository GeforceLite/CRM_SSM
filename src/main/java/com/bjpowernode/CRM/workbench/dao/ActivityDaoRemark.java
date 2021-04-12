package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.Activity;
import com.bjpowernode.CRM.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityDaoRemark {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkByAid(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);
}
