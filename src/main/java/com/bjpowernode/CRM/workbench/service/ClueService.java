package com.bjpowernode.CRM.workbench.service;

import com.bjpowernode.CRM.workbench.domain.Clue;

public interface ClueService {
    Boolean save(Clue clue);

    Clue detail(String id);

    Boolean unbund(String id);

    Boolean bund(String cid, String[] aids);
}
