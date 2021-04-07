package com.bjpowernode.CRM.settings.service;

import com.bjpowernode.CRM.settings.domain.User;
import com.bjpowernode.CRM.exception.LoginException;

import java.util.List;

/**
 * Author 北京动力节点
 */
public interface UserService {//实现业务也要抛出登录异常
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
