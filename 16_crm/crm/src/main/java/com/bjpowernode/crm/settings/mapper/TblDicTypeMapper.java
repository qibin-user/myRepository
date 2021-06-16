package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.TblDicType;
import com.bjpowernode.crm.settings.domain.TblDicTypeExample;

import java.util.List;


import org.apache.ibatis.annotations.Param;

public interface TblDicTypeMapper {

    int countByExample(TblDicTypeExample example);

    int deleteByExample(TblDicTypeExample example);

    int deleteByPrimaryKey(String code);

    int insert(TblDicType record);

    int insertSelective(TblDicType record);

    List<TblDicType> selectByExample(TblDicTypeExample example);

    TblDicType selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") TblDicType record, @Param("example") TblDicTypeExample example);

    int updateByExample(@Param("record") TblDicType record, @Param("example") TblDicTypeExample example);

    int updateByPrimaryKeySelective(TblDicType record);

    int updateByPrimaryKey(TblDicType record);

    /*
     * 字典表模块功能
     *
     * */
    //列表
    List<TblDicType> selectALlTblDicType();

    //增加字典类型
    int insertTblDicType(TblDicType tblDicType);

    //修改字典类型
    int updateByCode(TblDicType tblDicType);

    //修改字典类型 页面显现查询
    TblDicType selectByCode(String code);

    //批量删除字典类型
    int deleteBatchTblDicType(String[] codes);


}