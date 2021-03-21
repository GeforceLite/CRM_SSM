package com.bjowernode.CRM.settings.service;

import com.bjowernode.CRM.settings.dao.UserDao;
import com.bjowernode.CRM.utils.SqlSessionUtil;

public class UserServiceImpl implements UserDao {
    //这里是业务层，调用dao层，也就是把dao层对象创建出来(用工具)
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

}
