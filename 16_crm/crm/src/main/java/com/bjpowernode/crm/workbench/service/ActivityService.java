package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.domain.TblActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * 齐斌
 * 2021/5/25
 */
public interface ActivityService {

    List<TblActivity> selectAllActivity();

    TblActivity selectActivityById(String id);

    List<TblActivity> selectAllActivityLimit(int pageNo ,int pageSize);

    long selectAllCount();

    List<TblActivity> selectByCondition(Map map);

    int insertSelective(TblActivity tblActivity);

    int updateActivity(TblActivity tblActivity);

    int deleteActivity(String[ ] ids);

    TblActivity selectActivityByIdService(String id);

    List<TblActivityRemark> selectAllActivityRemarkService();

}
