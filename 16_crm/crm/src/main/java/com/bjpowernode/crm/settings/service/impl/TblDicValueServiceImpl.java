package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.mapper.TblDicValueMapper;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 齐斌
 * 2021/5/21
 */
@Service
public class TblDicValueServiceImpl implements TblDicValueService {

    @Autowired
    private TblDicValueMapper tblDicValueMapper;

    @Override
    public List<TblDicValue> selectAllTblDicValue() {
        return  tblDicValueMapper.selectAllTblDicValue();
    }

    @Override
    public TblDicValue selectByDicValue(String dicValue) {
        return tblDicValueMapper.selectByDicValue(dicValue);
    }

    @Override
    public int insert(TblDicValue tblDicValue) {
        return tblDicValueMapper.insert(tblDicValue);
    }

    @Override
    public TblDicValue selectByPrimaryKey(String id) {
        return tblDicValueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TblDicValue tblDicValue) {
        return tblDicValueMapper.updateByPrimaryKeySelective(tblDicValue);
    }

    @Override
    public int deleteBatchById(String[] ids) {
        return tblDicValueMapper.deleteBatchById(ids);
    }

    @Override
    public List<TblDicValue> selectDicValueAsOfDicType(String dicType) {
        return tblDicValueMapper.selectDicValueAsOfDicType(dicType);
    }
}
