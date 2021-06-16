package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.TblDicType;
import com.bjpowernode.crm.settings.mapper.TblDicTypeMapper;
import com.bjpowernode.crm.settings.service.TblDicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 齐斌
 * 2021/5/21
 */
@Service
public class TblDicTypeServiceImpl implements TblDicTypeService {

    @Autowired
    private TblDicTypeMapper tblDicTypeMapper;


    @Override
    public List<TblDicType> selectALlTblDicType() {
        return tblDicTypeMapper.selectALlTblDicType();
    }

    @Override
    public int addTblDicType(TblDicType tblDicType) {
        return tblDicTypeMapper.insertTblDicType(tblDicType);
    }

    @Override
    public int updateByCode(TblDicType tblDicType) {
        return tblDicTypeMapper.updateByCode(tblDicType);
    }

    @Override
    public TblDicType selectByCode(String code) {
        return tblDicTypeMapper.selectByCode(code);
    }

    @Override
    public int deleteBatchTblDicType(String[] codes) {
        return tblDicTypeMapper.deleteBatchTblDicType(codes);
    }
}
