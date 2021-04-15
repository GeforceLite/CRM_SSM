package com.bjpowernode.CRM.settings.dao;

import com.bjpowernode.CRM.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
