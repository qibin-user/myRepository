package com.bjpowernode.crm.workbench.service;


import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.TblActivity;

import java.util.List;
import java.util.Map;

/**
 * 齐斌
 * 2021/5/29
 */
public interface ClueService {

    List<Clue> selectAllClueService ();

    Clue selectClueById(String clueId);

    List<ClueActivityRelation> selectClueActivityRelationByClueIdService(String clueId);

    List<TblActivity> selectNotRelationActivityByIdAndVagueNameService(String clueId, String VagueName);

    int deleteClueActivityRelationByClueIdandActivityId(ClueActivityRelation clueActivityRelation);

    Object disposeClueConversion(Map<String,Object> map);

}
