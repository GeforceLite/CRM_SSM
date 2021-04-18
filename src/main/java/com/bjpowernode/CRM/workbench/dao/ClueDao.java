package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.Clue;
import com.bjpowernode.CRM.workbench.domain.ClueActivityRelation;

public interface ClueDao {
    int save(Clue clue);

    Clue detail(String id);

    int unbund(String id);

    int bund(ClueActivityRelation clueActivityRelation);
}
