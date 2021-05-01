package com.bjpowernode.CRM.workbench.service;

import com.bjpowernode.CRM.workbench.domain.Tran;
import com.bjpowernode.CRM.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    Boolean save(Tran tran, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListById(String tranId);

    Boolean changeStage(Tran tran);

    Map<String, Object> getCharts();
}
