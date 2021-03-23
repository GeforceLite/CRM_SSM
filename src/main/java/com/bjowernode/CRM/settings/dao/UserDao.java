package com.bjowernode.CRM.settings.dao;

import com.bjowernode.CRM.settings.domain.User;

import java.util.Map;

public interface UserDao {
    User login(Map<String, String> map);
    //每一个Dao层接口都对应一个同名的Mapper.xml映射文件，用于配置Mybatis

}
