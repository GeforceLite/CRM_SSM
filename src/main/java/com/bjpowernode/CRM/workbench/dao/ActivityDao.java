package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

//市场活动接口
public interface ActivityDao {
    int save(Activity activity);

    int getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity detail(String id);

    List getActivityListByClue(String id);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);
}
