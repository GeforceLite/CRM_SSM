package com.bjpowernode.CRM.workbench.dao;

public interface ActivityDaoRemark {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);
}
