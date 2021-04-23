package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer customer);
}
