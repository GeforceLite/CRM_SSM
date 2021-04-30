package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getHistoryListById(String tranId);
}
