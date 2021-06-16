package com.bjpowernode.crm.settings.service;


import com.bjpowernode.crm.settings.domain.TblDicType;

import java.util.List;

/**
 * 齐斌
 * 2021/5/21
 */
public interface TblDicTypeService {

    //列表
    List<TblDicType> selectALlTblDicType();

    //增加字典类型
    int addTblDicType(TblDicType tblDicType);

    //修改字典类型
    int updateByCode(TblDicType tblDicType);

    //修改字典类型 页面显现查询
    TblDicType selectByCode(String code);

    //批量删除字典类型
    int deleteBatchTblDicType(String[] codes);



}
