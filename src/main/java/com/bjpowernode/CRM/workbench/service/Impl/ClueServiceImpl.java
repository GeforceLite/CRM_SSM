package com.bjpowernode.CRM.workbench.service.Impl;

import com.bjpowernode.CRM.utils.ServiceFactory;
import com.bjpowernode.CRM.utils.SqlSessionUtil;
import com.bjpowernode.CRM.workbench.dao.ClueDao;
import com.bjpowernode.CRM.workbench.domain.Clue;
import com.bjpowernode.CRM.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
}
