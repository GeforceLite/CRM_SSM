package com.bjpowernode.CRM.settings.dao;

import com.bjpowernode.CRM.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User login(Map<String, String> map);

    List getUserList();
    //每一个Dao层接口都对应一个同名的Mapper.xml映射文件，用于配置Mybatis

}
