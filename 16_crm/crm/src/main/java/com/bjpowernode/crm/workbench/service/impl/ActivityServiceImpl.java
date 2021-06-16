package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.mapper.TblActivityMapper;
import com.bjpowernode.crm.workbench.mapper.TblActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 齐斌
 * 2021/5/25
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private TblActivityMapper tblActivityMapper;
    @Autowired
    private TblActivityRemarkMapper tblActivityRemarkMapper;

    /*
    * 查询所有
    * */
    public List<TblActivity> selectAllActivity(){

        return tblActivityMapper.selectAllActivity();
    }

    @Override
    public TblActivity selectActivityById(String id) {
        return tblActivityMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TblActivity> selectAllActivityLimit(int pageNo, int pageSize) {
        return tblActivityMapper.selectAllActivityLimit(pageNo, pageSize);
    }

    @Override
    public long selectAllCount() {
        return tblActivityMapper.selectAllCount();
    }

    @Override
    public List<TblActivity> selectByCondition(Map map) {
        return tblActivityMapper.selectByCondition(map);
    }

    @Override
    public int insertSelective(TblActivity tblActivity) {
        return tblActivityMapper.insertSelective(tblActivity);
    }

    @Override
    public int updateActivity(TblActivity tblActivity) {
        return tblActivityMapper.updateByPrimaryKeySelective(tblActivity);
    }

    @Override
    public int deleteActivity(String[] ids) {
        return tblActivityMapper.deleteActivityBatch(ids);
    }

    @Override
    public TblActivity selectActivityByIdService(String id) {
        return tblActivityMapper.selectActivityById(id);
    }

    @Override
    public List<TblActivityRemark> selectAllActivityRemarkService() {
        return tblActivityRemarkMapper.selectAllActivityRemark();
    }


}
