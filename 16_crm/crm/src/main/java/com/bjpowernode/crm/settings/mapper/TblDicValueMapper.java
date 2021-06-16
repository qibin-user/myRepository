package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.domain.TblDicValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblDicValueMapper {
    int countByExample(TblDicValueExample example);

    int deleteByExample(TblDicValueExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblDicValue record);

    int insertSelective(TblDicValue record);

    List<TblDicValue> selectByExample(TblDicValueExample example);

    TblDicValue selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblDicValue record, @Param("example") TblDicValueExample example);

    int updateByExample(@Param("record") TblDicValue record, @Param("example") TblDicValueExample example);

    int updateByPrimaryKeySelective(TblDicValue record);

    int updateByPrimaryKey(TblDicValue record);

    /*
     *
     * 查询所有字典值
     * */
    List<TblDicValue> selectAllTblDicValue();
    /*
    *  根据字典值查询
    * */
    TblDicValue selectByDicValue( String dicValue);

    /*
    * 删除一批
    * */
    int deleteBatchById(String[] ids);

    /*
        根据字典类型 查询 字典值
    * List<TblDicValue> selectDicValueAsOfDicType(String DicType);
    * */
    List<TblDicValue> selectDicValueAsOfDicType(String DicType);





}