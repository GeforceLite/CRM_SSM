package com.bjpowernode.CRM.workbench.service.Impl;

import com.bjpowernode.CRM.settings.dao.UserDao;
import com.bjpowernode.CRM.settings.domain.User;
import com.bjpowernode.CRM.utils.SqlSessionUtil;
import com.bjpowernode.CRM.vo.PaginationVO;
import com.bjpowernode.CRM.workbench.dao.ActivityDao;
import com.bjpowernode.CRM.workbench.dao.ActivityDaoRemark;
import com.bjpowernode.CRM.workbench.domain.Activity;
import com.bjpowernode.CRM.workbench.domain.ActivityRemark;
import com.bjpowernode.CRM.workbench.service.ActivityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    //ActivityService接口实现类
    //new Dao层代理对象,用哪张表了,就得用哪个dao的映射文件嘛
    //市场活动dao
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    //市场活动备注dao
    private ActivityDaoRemark activityDaoRemark = SqlSessionUtil.getSqlSession().getMapper(ActivityDaoRemark.class);
    //用户表Dao
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        List<Activity> list = new ArrayList<>();
        list=activityDao.getActivityListByNameAndNotByClueId(map);
        return list;
    }

    @Override
    public List getActivityListByClue(String id) {
        List list = new ArrayList();
        list=activityDao.getActivityListByClue(id);
        return list;
    }

    //市场活动备注信息修改方法
    @Override
    public Boolean updateRemark(ActivityRemark activityRemark) {
        Boolean flag = null;
        int count=activityDaoRemark.updateRemark(activityRemark);
        if (count==1){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }

    //市场活动备注信息添加方法
    @Override
    public Boolean saveRemark(ActivityRemark activityRemark) {
        Boolean flag = null;
        int count=activityDaoRemark.saveRemark(activityRemark);
        if (count==1){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }

    //市场活动备注信息删除方法
    @Override
    public Boolean deleteRemark(String id) {
        Boolean resultFlag = null;
        int result = activityDaoRemark.deleteRemark(id);
        if (result==1){
            resultFlag = true;
        }else {
            resultFlag = false;
        }
        return resultFlag;
    }

    //市场活动备注信息查询方法
    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> list=activityDaoRemark.getRemarkListByAid(activityId);
        return list;
    }

    //市场活动详细页面
    @Override
    public Activity detail(String id) {
        Activity activity=activityDao.detail(id);
        return activity;
    }

    //更新市场活动操作列表
    @Override
    public boolean update(Activity activity) {
        boolean flag = true;
        int count = activityDao.update(activity);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    //查询信息列表和根据市场活动id查询单条记录操作（编辑操作前期铺垫)
    @Override
    public Map<String, Object>getUserListAndActivity(String id) {
        //取uList,之前写过这个，复用就行
        List<User> uList=userDao.getUserList();
        //取a
        Activity activity=activityDao.getById(id);
        //把uList和a都打包成map发出去
        Map<String, Object> map = new HashMap<>();
        map.put("uList", uList);
        map.put("a", activity);
        return map;
    }

    //客户列表删除操作
    @Override
    public boolean delete(String[] ids) {
        System.out.println("删除业务层进入");
        //因为还有市场活动备注，所以要考虑备注删除到的一些关联表
        boolean flag = true;
        //先查询出将要要删除的备注数量
        int count1 = activityDaoRemark.getCountByAids(ids);
        System.out.println(count1);
        //再查出实际的影响到的条数用于后续比对
        int count2 = activityDaoRemark.deleteByAids(ids);
        if (count1!=count2){
            flag = false;
        }
        //最后删除实际市场活动
        int count3 = activityDao.delete(ids);
        if (count3!= ids.length){
            flag = false;
        }
        return flag;
    }

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
