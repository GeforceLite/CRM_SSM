package com.bjpowernode.CRM.workbench.service;

import com.bjpowernode.CRM.workbench.domain.Tran;
import com.bjpowernode.CRM.workbench.domain.TranHistory;

import java.util.List;

public interface TranService {
    Boolean save(Tran tran, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListById(String tranId);
}
