package com.bjpowernode.CRM.workbench.service;

import com.bjpowernode.CRM.vo.PaginationVO;
import com.bjpowernode.CRM.workbench.domain.Activity;
import com.bjpowernode.CRM.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

//市场活动服务接口
public interface ActivityService {

    boolean save(Activity activity);

    PaginationVO pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    Boolean deleteRemark(String id);

    Boolean saveRemark(ActivityRemark activityRemark);

    Boolean updateRemark(ActivityRemark activityRemark);

    List getActivityListByClue(String id);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);
}
