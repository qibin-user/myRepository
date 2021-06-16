package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.domain.TblActivityRemarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblActivityRemarkMapper {
    int countByExample(TblActivityRemarkExample example);

    int deleteByExample(TblActivityRemarkExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblActivityRemark record);

    int insertSelective(TblActivityRemark record);

    List<TblActivityRemark> selectByExample(TblActivityRemarkExample example);

    TblActivityRemark selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblActivityRemark record, @Param("example") TblActivityRemarkExample example);

    int updateByExample(@Param("record") TblActivityRemark record, @Param("example") TblActivityRemarkExample example);

    int updateByPrimaryKeySelective(TblActivityRemark record);

    int updateByPrimaryKey(TblActivityRemark record);

    /*
    * 查询全部活动备注
    * */
    List<TblActivityRemark> selectAllActivityRemark ();

}