package com.bjpowernode.crm.workbench.web.controller;


import com.bjpowernode.crm.commons.contents.content;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.MyUtil;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;


/**
 * 齐斌
 * 2021/5/25
 */
@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    //int pageNo, int pageSize, String owner, String name, String startDate, String endDate ,
    /*
     * 到活动界面
     * */
    @RequestMapping("/workbench/activity/index.do")
    public String toIndex(HttpServletRequest request) {

        List<User> userList = userService.selectAllUsers();
        request.setAttribute("userList", userList);
        return "workbench/activity/index";
    }

    /*
     * 展示活动界面数据
     * */
    @RequestMapping("/workbench/activity/queryActivityForPageByCondition.do")
    public @ResponseBody
    Object queryActivityForPageByCondition(int pageNo, int pageSize, String owner, String name, String startDate, String endDate) {
        //创建map 方便传参
        Map<String, Object> map = new HashMap<>();
        map.put("pageStart", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("owner", owner);
        map.put("name", name);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        //调用多条件查询
        List<TblActivity> activityList = activityService.selectByCondition(map);
        //查询数据总条数
        long l = activityService.selectAllCount();
        //封装到map中
        Map<String, Object> map1 = new HashMap<>();
        map1.put("activityList", activityList);
        map1.put("totalRows", l);

        return map1;
    }

    /*
     * 添加活动
     * */
    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody
    Object saveCreateActivity(TblActivity tblActivity, HttpSession session) {
        User user = (User) session.getAttribute(content.SESSION_USER);
        tblActivity.setId(UUIDUtils.getUUID());
        tblActivity.setCreateTime(DateUtils.formatDateTime(new Date()));
        tblActivity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();

        try {
            int i = activityService.insertSelective(tblActivity);

            if (i > 0) {
                returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("添加失败");
        }

        return returnObject;
    }

    /*
     * 单击修改按钮
     * */
    @RequestMapping("/workbench/activity/editActivity.do")
    public @ResponseBody
    Object editActivity(String id) {
        TblActivity activity = activityService.selectActivityById(id);
        return activity;
    }

    /*
     * 点击保存按钮 修改活动
     * */

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody
    Object saveEditActivity(TblActivity tblActivity, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(content.SESSION_USER);
        tblActivity.setEditBy(user.getName());
        tblActivity.setEditTime(DateUtils.formatDateTime(new Date()));

        int i = activityService.updateActivity(tblActivity);

        if (i > 0) {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("添加失败");
        }

        return returnObject;

    }

    /*
     * 批量删除
     * */
    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    public @ResponseBody
    Object deleteActivityByIds(@RequestParam("array[]") String[] array) {
        ReturnObject returnObject = new ReturnObject();


        int i = activityService.deleteActivity(array);
        if (i > 0) {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("删除失败");
        }
        return returnObject;
    }

    /*
     * 批量导出
     * */
    @RequestMapping("/workbench/activity/exportAllActivity.do")
    public void exportAllActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<TblActivity> activityList = activityService.selectAllActivity();

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);

        cell.setCellValue("id");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始时间");
        cell = row.createCell(4);
        cell.setCellValue("结束时间");

        if (activityList != null) {
            TblActivity tblActivity = null;
            for (int i = 0; i < activityList.size(); i++) {
                tblActivity = activityList.get(i);
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(tblActivity.getId());
                cell = row.createCell(1);
                cell.setCellValue(tblActivity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(tblActivity.getName());
                cell = row.createCell(3);
                cell.setCellValue(tblActivity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(tblActivity.getEndDate());

            }
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
//       String fileName = URLEncoder.encode("市场活动表", "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=activity.xls");

        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.close();
        wb.close();
    }

    /*
     * 选择导出
     * */
    @RequestMapping("/workbench/activity/exportByIdActivity.do")
    public void exportByIdActivity(String id, HttpServletResponse response) throws IOException {

        TblActivity tblActivity = activityService.selectActivityById(id);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);

        cell.setCellValue("id");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始时间");
        cell = row.createCell(4);
        cell.setCellValue("结束时间");

        if (tblActivity != null) {
            row = sheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue(tblActivity.getId());
            cell = row.createCell(1);
            cell.setCellValue(tblActivity.getOwner());
            cell = row.createCell(2);
            cell.setCellValue(tblActivity.getName());
            cell = row.createCell(3);
            cell.setCellValue(tblActivity.getStartDate());
            cell = row.createCell(4);
            cell.setCellValue(tblActivity.getEndDate());
        }

        response.setContentType("application/octet-stream;charset=UTF-8");
//       String fileName = URLEncoder.encode("市场活动表", "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=activity.xls");

        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.close();
        wb.close();
    }

    /*
     * 导入活动
     * */
    @RequestMapping("/workbench/activity/importActivity.do")
    public @ResponseBody
    Object importActivity(String userName, MultipartFile activityFile, HttpSession session) throws IOException {
        User user = (User) session.getAttribute(content.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();

        List<TblActivity> activitylist = new ArrayList<>();
        InputStream is = activityFile.getInputStream();
        HSSFWorkbook wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = null;
        HSSFCell cell = null;
        TblActivity tblActivity = null;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.createRow(i);
            tblActivity = new TblActivity();
            tblActivity.setId(UUIDUtils.getUUID());
            tblActivity.setOwner(user.getId());
            tblActivity.setCreateBy(user.getId());
            tblActivity.setCreateTime(DateUtils.formatDateTime(new Date()));

            for (int j = 0; j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                String cellValue = MyUtil.getCellValue(cell);

                if(j == 0){
                    tblActivity.setName(cellValue);


                }else if(j == 1){
                    tblActivity.setStartDate(cellValue);
                }else if (j == 2){
                    tblActivity.setEndDate(cellValue);
                }else if (j == 3){
                    tblActivity.setCost(cellValue);
                }else if(j == 4){
                    tblActivity.setDescription(cellValue);
                }
            }
            activitylist.add(tblActivity);
        }
        return null;
    }
    /*
    *    详情界面
    * workbench/activity/detailActivity.do
    * */

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String toDetailActivity(String id,HttpServletRequest request){
        TblActivity tblActivity = activityService.selectActivityByIdService(id);
        List<TblActivityRemark> remarkList = activityService.selectAllActivityRemarkService();
        request.setAttribute("activity", tblActivity);
        request.setAttribute("remarkList", remarkList);
        return "workbench/activity/detail";
    }


}
