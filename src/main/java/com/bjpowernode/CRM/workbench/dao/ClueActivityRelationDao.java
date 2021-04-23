package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
