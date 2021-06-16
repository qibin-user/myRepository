package com.bjpowernode.crm.workbench.service.impl;


import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueService;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 齐斌
 * 2021/5/29
 */
@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private TranMapper tranMapper;

    @Override
    public List selectAllClueService() {
        return clueMapper.selectAllClue();
    }

    @Override
    public Clue selectClueById(String clueId) {
        return clueMapper.selectClueById(clueId);
    }

    @Override
    public List<ClueActivityRelation> selectClueActivityRelationByClueIdService(String clueId) {
        return clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
    }

    @Override
    public List<TblActivity> selectNotRelationActivityByIdAndVagueNameService(String clueId, String VagueName) {
        return   clueActivityRelationMapper.selectNotRelationActivityByIdAndVagueName(clueId,VagueName);
    }

    @Override
    public int deleteClueActivityRelationByClueIdandActivityId(ClueActivityRelation clueActivityRelation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdActivityId(clueActivityRelation);
    }
    /*
    * map.put("clue", clue);
    * map.put("isCreateTran", isCreateTran);
    * map.put("amountOfMoney", amountOfMoney);
    * map.put("tradeName", tradeName);
    * map.put("expectedClosingDate", expectedClosingDate);
    * map.put("stage", stage);
    * map.put("activityId", activityId);
    * map.put("user", user);
    * */
    @Override
    public Object disposeClueConversion(Map<String, Object> map) {
        //获取信息
        Clue clue = (Clue) map.get("clue");
        String isCreateTran= (String) map.get("isCreateTran");
        String amountOfMoney= (String) map.get("amountOfMoney");
        String tradeName= (String) map.get("tradeName");
        String expectedClosingDate= (String) map.get("expectedClosingDate");
        String stage= (String) map.get("stage");
        User user = (User) map.get("user");


        //创建客户
        Customer customer = new Customer();
        customer.setId(UUIDUtils.getUUID());
        customer.setOwner(user.getId());
        customer.setName(clue.getCompany());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formatDateTime(new Date()));
        customer.setContactSummary(clue.getContactSummary());
        customer.setDescription(clue.getDescription());
        customer.setAddress(clue.getAddress());
        customerMapper.insertCustomer(customer);


        //创建联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtils.getUUID());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contacts.setMphone(clue.getMphone());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setDescription(clue.getDescription());
        contacts.setAddress(clue.getAddress());
        contactsMapper.insertContacts(contacts);

        if(isCreateTran == "true"){
            //创建交易
            Tran tran = new Tran();
            tran.setId(UUIDUtils.getUUID());
            tran.setOwner(user.getId());
            tran.setMoney(amountOfMoney);
            tran.setName(tradeName);
            tran.setExpectedDate(expectedClosingDate);
            tran.setCustomerId(customer.getId());
            tran.setStage(stage);
            tranMapper.insertTran(tran);
        }


        //删除clue
        clueMapper.deleteByPrimaryKey(clue.getId());

        return null;
    }
}
