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

        //将字典类型列表取出
        List<DicType> dtList = dicTypeDao.getTypeList();

        //将字典类型列表遍历
        for(DicType dt : dtList){

            //取得每一种类型的字典类型编码
            String code = dt.getCode();

            //根据每一个字典类型来取得字典值列表
            List<DicValue> dvList = dicValueDao.getListByCode(code);
            System.out.println(code+"List");
            map.put(code+"List", dvList);


        }

        return map;
    }
}
