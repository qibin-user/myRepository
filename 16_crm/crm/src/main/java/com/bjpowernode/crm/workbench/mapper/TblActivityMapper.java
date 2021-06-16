package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.domain.TblActivityExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TblActivityMapper {
    int countByExample(TblActivityExample example);

    int deleteByExample(TblActivityExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblActivity record);

    int insertSelective(TblActivity record);

    List<TblActivity> selectByExample(TblActivityExample example);

    TblActivity selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblActivity record, @Param("example") TblActivityExample example);

    int updateByExample(@Param("record") TblActivity record, @Param("example") TblActivityExample example);

    int updateByPrimaryKeySelective(TblActivity record);

    int updateByPrimaryKey(TblActivity record);

    /*
    * 查询 all activity
    * */
    List<TblActivity> selectAllActivity();
    /*
    * 查询 all activity limit 分页查询所有
    * */
    List<TblActivity> selectAllActivityLimit(@Param("pageNo") int pageNo ,@Param("pageSize")int pageSize);

    /*
    * 条件查询
    * */
    List<TblActivity> selectByCondition(Map map);

    long selectAllCount();


    /*
    * 批量删除
    * */
    int deleteActivityBatch(String[] ids);
    /*
    * 通过地查询
    *selectByIdActivity
    * */
    TblActivity selectByIdActivity(String id);

    /*
    * 查询activity 详情界面
    *   所有者
    *   创建者
    *   修改者
    * */
    TblActivity selectActivityById(String id);







}
