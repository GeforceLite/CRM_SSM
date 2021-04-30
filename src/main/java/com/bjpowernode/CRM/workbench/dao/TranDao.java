package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.Customer;
import com.bjpowernode.CRM.workbench.domain.Tran;

public interface TranDao {

    int save(Tran tran);

    Tran detail(String id);

    int changeStage(Tran tran);
}
