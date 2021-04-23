package com.bjpowernode.CRM.workbench.service.Impl;

import com.bjpowernode.CRM.utils.DateTimeUtil;
import com.bjpowernode.CRM.utils.ServiceFactory;
import com.bjpowernode.CRM.utils.SqlSessionUtil;
import com.bjpowernode.CRM.utils.UUIDUtil;
import com.bjpowernode.CRM.workbench.dao.*;
import com.bjpowernode.CRM.workbench.domain.*;
import com.bjpowernode.CRM.workbench.service.ClueService;

import java.util.List;

public class ClueServiceImpl implements ClueService {
    //线索相关表
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    //客户相关表
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    //联系人相关表
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    //交易相关表
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public Boolean convert(String clueId, Tran tran, String createBy) {
        Boolean flag = true;
        String createTime = DateTimeUtil.getSysTime();
      //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue clue = clueDao.getById(clueId);
      //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if (customer==null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(company);
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            customer.setContactSummary(clue.getContactSummary());
            //添加客户
            int count1 = customerDao.save(customer);
            if (count1!=1){
                flag = false;
            }
        }
      //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setId(contacts.getId());
        contacts.setContactSummary(contacts.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());
        //添加联系人
        int count2=contactsDao.save(contacts);
        if (count2!=1){
            flag = false;
        }

      //(4) 线索备注转换到客户备注以及联系人备注,把备注搬到这两张表里面
        //查询出与该线索关联的用户信息列表
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        for (ClueRemark clueRemark:clueRemarkList){
            //取出每一条线索的备注
            String noteContent = clueRemark.getNoteContent();
            //创建客户备注对象，添加客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(noteContent);
            int count3 = customerRemarkDao.save(customerRemark);
            if (count3!=1){
                flag = false;
            }
            //创建联系人备注对象，添加联系人备注
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4!=1){
                flag = false;
            }
        }
      //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        //查询出与该条线索关联的市场活动
        List<ClueActivityRelation> clueActivityRelationList=clueActivityRelationDao.getListByClueId(clueId);
        for (ClueActivityRelation ClueActivityRelation:clueActivityRelationList){
            //取得关联的市场活动Id
            String activityId = ClueActivityRelation.getActivityId();
            //创建联系人与市场活动的关联关系对象，让第三步生成的联系人与市场活动做关联
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());
            //添加关联关系
            int count5=contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5!=1){
                flag = false;
            }
        }
      //(6) 如果有创建交易需求，创建一条交易,有tran代表了那个创建交易的对钩打上了，创建交易
      //反之如果tran是null的话就代表没有发起那个交易创建
        if (tran!=null){
            //tran对象，已经封装好的信息如下id,name,expectDate,stage,activityId,createBy,createTime
            //信息补充一下，可转可不转
            tran.setSource(clue.getSource());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setContactsId(contacts.getId());
            //交易记录保存
            int count6 = tranDao.save(tran);
            if (count6!=1){
                flag = false;
            }
            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setStage(tran.getStage());
            tranHistory.setTranId(tran.getId());
            //添加交易历史
            int count7 = tranHistoryDao.save(tranHistory);
            if(count7!=1){
                flag = false;
            }
        }

      //(8) 删除线索备注
        for (ClueRemark clueRemark : clueRemarkList) {
            int count8 = clueRemarkDao.delete(clueRemark);
            if(count8!=1){
                flag = false;
            }
        }
      //(9) 删除线索和市场活动的关系
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
            int count9=clueActivityRelationDao.delete(clueActivityRelation);
            if (count9!=1){
                flag = false;
            }
        }
      //(10) 删除线索
        int count10 = clueDao.delete(clueId);
        if (count10!=1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Boolean bund(String cid, String[] aids) {
        boolean flag = true;
        for (String aid : aids) {
            //取得每一个aid和cid做关联
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            //读取数组中的每一个activityId（aid）
            clueActivityRelation.setActivityId(aid);
            //读取clueId
            clueActivityRelation.setClueId(cid);
            //添加关联关系表中的记录
            int count=clueDao.bund(clueActivityRelation);
            if (count == 1) {
                flag = true;
            }else {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public Boolean unbund(String id) {
        Boolean flag = true;
        int count=clueDao.unbund(id);
        if (count==1){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }

    @Override
    public Clue detail(String id) {
        System.out.println(id);
        boolean result = true;
        Clue clue=clueDao.detail(id);
        return clue;
    }

    //线索添加方法
    @Override
    public Boolean save(Clue clue) {
        Boolean result = true;
        int count=clueDao.save(clue);
        if (count==1){
            result = true;
        }else{
            result = false;
        }
        return result;
    }
}
