package com.bjpowernode.CRM.workbench.dao;

import com.bjpowernode.CRM.workbench.domain.Clue;

public interface ClueDao {
    int save(Clue clue);

    Clue detail(String id);

    int unbund(String id);
}
