package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer customer);

    List<String> getCustomerName(String name);

    int add(Customer customer);
}
