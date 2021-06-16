package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.TblDicValue;

import java.util.List;

/**
 * 齐斌
 * 2021/5/21
 */
public interface TblDicValueService {

    /*
     *
     * 查询所有字典值
     * */
    List<TblDicValue> selectAllTblDicValue();

    /*
    * 根据字典值查询
    * */
    TblDicValue selectByDicValue( String dicValue);

    /*
    * 添加字典值
    * */
    int insert(TblDicValue tblDicValue);

    /*
    * 通过id 查询字典值
    * */
    TblDicValue selectByPrimaryKey(String id);

    /*
    * 修改 字典值
    * */
    int updateByPrimaryKeySelective(TblDicValue tblDicValue);
    /*
    * 删除一批 字典值
    * */
    int deleteBatchById(String[] ids);
    /*
    * 根据字典类型 查找 字典值
    *
    * */
    List<TblDicValue> selectDicValueAsOfDicType(String dicType);
}
