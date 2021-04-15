package com.bjpowernode.CRM.settings.service.impl;

import com.bjpowernode.CRM.settings.dao.DicTypeDao;
import com.bjpowernode.CRM.settings.dao.DicValueDao;
import com.bjpowernode.CRM.settings.domain.DicType;
import com.bjpowernode.CRM.settings.domain.DicValue;
import com.bjpowernode.CRM.settings.service.DicService;
import com.bjpowernode.CRM.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    //数据字典全查
    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();
        //将字典类型列表先取出，再根据字典类型分门别类的对对应值进行查询
        List<DicType> typeList = dicTypeDao.getTypeList();
        for (DicType dicType : typeList){
            String code = dicType.getCode();
            List<DicValue> list = dicValueDao.getListByCode(code);
            map.put("code="+code,list);
        }
        //取出来列表遍历，边遍历边往里面传值查询
        return map;
    }
}
