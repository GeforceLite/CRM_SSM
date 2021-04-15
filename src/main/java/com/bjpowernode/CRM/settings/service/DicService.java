package com.bjpowernode.CRM.settings.service;

import com.bjpowernode.CRM.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {

    Map<String, List<DicValue>> getAll();
}
