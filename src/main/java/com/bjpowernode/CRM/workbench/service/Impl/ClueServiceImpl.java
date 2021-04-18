package com.bjpowernode.CRM.workbench.service.Impl;

import com.bjpowernode.CRM.utils.ServiceFactory;
import com.bjpowernode.CRM.utils.SqlSessionUtil;
import com.bjpowernode.CRM.utils.UUIDUtil;
import com.bjpowernode.CRM.workbench.dao.ClueDao;
import com.bjpowernode.CRM.workbench.domain.Clue;
import com.bjpowernode.CRM.workbench.domain.ClueActivityRelation;
import com.bjpowernode.CRM.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

    @Override
    public Boolean bund(String cid, String[] aids) {
        boolean flag = true;
        for (String aid : aids) {
            //取得每一个aid和cid做关联
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            //读取数组中的每一个activityId（aid）
            clueActivityRelation.setActivityId(aid);
            //读取clueId
            clueActivityRelation.setClueId(cid);
            //添加关联关系表中的记录
            int count=clueDao.bund(clueActivityRelation);
            if (count == 1) {
                flag = true;
            }else {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public Boolean unbund(String id) {
        Boolean flag = true;
        int count=clueDao.unbund(id);
        if (count==1){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }

    @Override
    public Clue detail(String id) {
        System.out.println(id);
        boolean result = true;
        Clue clue=clueDao.detail(id);
        return clue;
    }

    //线索添加方法
    @Override
    public Boolean save(Clue clue) {
        Boolean result = true;
        int count=clueDao.save(clue);
        if (count==1){
            result = true;
        }else{
            result = false;
        }
        return result;
    }
}
