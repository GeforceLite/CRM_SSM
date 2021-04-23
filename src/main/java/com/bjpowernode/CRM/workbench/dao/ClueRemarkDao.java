package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getListByClueId(String clueId);

    int delete(ClueRemark clueRemark);
}
