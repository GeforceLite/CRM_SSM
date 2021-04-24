package com.bjpowernode.CRM.workbench.service.Impl;

import com.bjpowernode.CRM.utils.ServiceFactory;
import com.bjpowernode.CRM.utils.SqlSessionUtil;
import com.bjpowernode.CRM.workbench.dao.CustomerDao;
import com.bjpowernode.CRM.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> list=customerDao.getCustomerName(name);
        return list;
    }
}
