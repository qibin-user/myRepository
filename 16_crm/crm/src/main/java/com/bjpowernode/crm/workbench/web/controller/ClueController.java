package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contents.content;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 齐斌
 * 2021/5/29
 */
@Controller
public class ClueController {

    @Autowired
    private ClueService clueService;

    @Autowired
    private TblDicValueService tblDicValueService;

    @Autowired
    private ActivityService activityService;




    /*
     * 跳转到线索界面
     * */
    @RequestMapping("/workbench/clue/index.do")
    public String toClueIndex(HttpSession session, HttpServletRequest request) {
        //获取所有线索
        List<Clue> clueList = clueService.selectAllClueService();
        request.setAttribute("clueList", clueList);
        //获取创建界面的 所有者
        User user = (User) session.getAttribute(content.SESSION_USER);
        request.setAttribute("user", user);
        //获取创建界面的 称呼
        List<TblDicValue> appellationList = tblDicValueService.selectDicValueAsOfDicType("appellation");
        request.setAttribute("appellationList", appellationList);
        //获取创建界面的 线索状态
        List<TblDicValue> clueStateList = tblDicValueService.selectDicValueAsOfDicType("clueState");
        request.setAttribute("clueStateList", clueStateList);
        //获取创建界面的 线索来源
        List<TblDicValue> sourceList = tblDicValueService.selectDicValueAsOfDicType("source");
        request.setAttribute("sourceList", sourceList);

        return "workbench/clue/index";
    }

    /*
     * 跳到线索详情界面
     * */
    @RequestMapping("/workbench/clue/detailClue.do")
    public String toDetailclue(String id, Model model) {
        //获取线索所有信息
        Clue clue = clueService.selectClueById(id);//id = clueId
        model.addAttribute("clue", clue);
        //获取线索详情中 市场活动
        //获取跟clue 有关的市场活动
        List<ClueActivityRelation> clueActivityRelationsList = clueService.selectClueActivityRelationByClueIdService(id);

        List<TblActivity> activityList = new ArrayList<>();
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationsList) {
            String activityId = clueActivityRelation.getActivityId();

            TblActivity tblActivity = activityService.selectActivityByIdService(activityId);

            activityList.add(tblActivity);
        }


        model.addAttribute("activityList", activityList);

        return "workbench/clue/detail";
    }




    /*
     * 处理线索详情界面 关联市场活动 搜索框
     * activityName:activityName,
     * clueId:clueId
     * */
    @RequestMapping("/workbench/clue/queryActivityForDetailByName.do")
    public @ResponseBody
    Object searchActivity(String activityName, String clueId) {
        //搜索框 搜索出来的只能是跟当前clueId 没有关联的 市场活动
        //并且支持 模糊搜索
        List<TblActivity> activityList = clueService.selectNotRelationActivityByIdAndVagueNameService(clueId, activityName);

        return activityList;
    }




    /*
     * 处理线索详情界面 市场活动 解除关联
     * workbench/clue/saveUnbundActivity.do
     *                  activityId:activityId,
						clueId:clueId
     * */
    @RequestMapping("/workbench/clue/saveUnbundActivity.do")
    public @ResponseBody
    Object saveUnbundActivity(ClueActivityRelation clueActivityRelation) {

        int i = clueService.deleteClueActivityRelationByClueIdandActivityId(clueActivityRelation);

        ReturnObject returnObject = new ReturnObject();
        if (i > 0) {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);

        } else {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("删除失败");
        }

        return returnObject;
    }

    /*
     * 处理 线索详情界面 转换界面
     * workbench/clue/convertClue.do?id=
     * */
    @RequestMapping("/workbench/clue/convertClue.do")
    public String convertClue(String id, HttpServletRequest request, HttpSession session) {//id  = clueId

        Clue clue = clueService.selectClueById(id);
        request.setAttribute("clue", clue);

        List<TblDicValue> stageList = tblDicValueService.selectDicValueAsOfDicType("stage");
        request.setAttribute("stageList", stageList);

        User user = (User) session.getAttribute(content.SESSION_USER);
        request.setAttribute("user", user);


        return "workbench/clue/convert";
    }

    /*
     * 处理 线索详情界面 转换界面 转换
     *
     * */
    @RequestMapping("/workbench/clue/saveConvertClue.do")
    public @ResponseBody
    Object saveConvertClue(String clueId, String isCreateTran, String amountOfMoney, String tradeName, String expectedClosingDate, String stage, String activityId,HttpSession session) {

        Map<String ,Object> map = new HashMap<>();
        User user = (User) session.getAttribute(content.SESSION_USER);
        Clue clue = clueService.selectClueById(clueId);
        map.put("clue", clue);
        map.put("isCreateTran", isCreateTran);
        map.put("amountOfMoney", amountOfMoney);
        map.put("tradeName", tradeName);
        map.put("expectedClosingDate", expectedClosingDate);
        map.put("stage", stage);
        map.put("activityId", activityId);
        map.put("user", user);

        ReturnObject o = (ReturnObject) clueService.disposeClueConversion(map);

        return o;
    }
}
