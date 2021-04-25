package com.bjpowernode.CRM.workbench.service;

import com.bjpowernode.CRM.workbench.domain.Tran;

public interface TranService {
    Boolean save(Tran tran, String customerName);

    Tran detail(String id);
}
