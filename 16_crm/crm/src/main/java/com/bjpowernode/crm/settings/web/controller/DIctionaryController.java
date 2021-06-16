package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contents.content;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.TblDicType;
import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.service.TblDicTypeService;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 齐斌
 * 2021/5/22
 */
@Controller
public class DIctionaryController {

    @Autowired
    private TblDicTypeService tblDicTypeService;

    @Autowired
    private TblDicValueService tblDicValueService;

    /*
     * 跳转到字典类型界面
     * settings/dictionary/type/index.do
     * */
    @RequestMapping("/settings/dictionary/type/index.do")
    public String toDicTypeindex(HttpServletRequest request) {

        List<TblDicType> dicTypeList = tblDicTypeService.selectALlTblDicType();
        request.setAttribute("dicTypeList", dicTypeList);
        return "settings/dictionary/type/index";
    }

    /*
     * 跳转到字典类型增加界面
     * */
    @RequestMapping("/settings/dictionary/type/toSave.do")
    public String toDicTypeSave() {

        return "settings/dictionary/type/save";
    }

    /*
     * 对字典类型创建判断code是否重复
     * */
    @RequestMapping("/settings/dictionary/type/estimateCode.do")
    public @ResponseBody
    Object estimateCode(String code) {
        TblDicType tblDicType = tblDicTypeService.selectByCode(code);
        ReturnObject returnObject = new ReturnObject();

        if (tblDicType != null) {
            //code 重复
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("编码重复");
        } else {
            //code不重复
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
        }

        return returnObject;
    }

    /*
     * 保存字典类型
     * */
    @RequestMapping("/settings/dictionary/type/insertType.do")
    public @ResponseBody
    Object insertType(TblDicType tblDicType) {

        ReturnObject returnObject = new ReturnObject();

        try {
            int i = tblDicTypeService.addTblDicType(tblDicType);

            if (i == 1) {
                //表示添加成功
                returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                //表示失败
                returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //表示失败
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
        }


        return returnObject;

    }

    /*
     * 字典类型界面 点击编辑 跳转到编辑界面
     * */
    @RequestMapping("/settings/dictionary/type/redactDicType.do")
    public String redactDicType(String code, HttpServletRequest request) {

        TblDicType tblDicType = tblDicTypeService.selectByCode(code);

        if (tblDicType != null) {
            //查到字典类型
            //把查到的字典类型内容 放到request作用域中
            request.setAttribute("dicType", tblDicType);
            return "/settings/dictionary/type/edit";

        } else {
            //没查到字典类型
            //证明有人在修改之前删除啦

            return "redirect:/settings/dictionary/type/index.do";

        }

    }

    /*
     *  修改字典类型内容
     * */
    @RequestMapping("/settings/dictionary/type/updataDicType.do")
    public @ResponseBody
    Object upDataDicType(TblDicType tblDicType) {

        ReturnObject returnObject = new ReturnObject();

        int i = tblDicTypeService.updateByCode(tblDicType);

        if (i == 1) {
            //表示修改成功
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            //表示失败
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
        }

        return returnObject;

    }

    /*
     * 批量删除 字典类型
     * */
    @RequestMapping("/settings/dictionary/type/deleteBatchType.do")
    public @ResponseBody
    Object deleteBatchType(@RequestParam("coeds[]") String[] codes) {
        ReturnObject returnObject = new ReturnObject();


        int i = tblDicTypeService.deleteBatchTblDicType(codes);

        if (i > 0) {
            //表示删除成功
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            //表示失败
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
        }

        return returnObject;

    }


    /*
     * 跳转到字典值界面
     * */
    @RequestMapping("/settings/dictionary/value/index.do")
    public String toDicValueIndex(HttpServletRequest request) {

        List<TblDicValue> dicValueList = tblDicValueService.selectAllTblDicValue();
        request.setAttribute("dicValueList", dicValueList);
        return "settings/dictionary/value/index";
    }

    /*
    *  跳转 创建 字典值界面
    * */
    @RequestMapping("/settings/dictionary/value/toSave.do")
    public String toSave(HttpServletRequest request){
        //获取下拉链表内容
        List<TblDicType> dicTypeList = tblDicTypeService.selectALlTblDicType();
        request.setAttribute("dicTypeList", dicTypeList);
        return "settings/dictionary/value/save";
    }

    /*
    * 判断 字典值是否重复
    * */
    @RequestMapping("/settings/dictionary/value/selectRepetition.do")
    public @ResponseBody Object selectRepetition(String dicValue){
        ReturnObject returnObject = new ReturnObject();

        TblDicValue tblDicValue = tblDicValueService.selectByDicValue(dicValue);

        if(tblDicValue == null){
            //没有重复
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
        }else{
            //重复
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
        }

        return returnObject;
    }

    /*
    * 添加 字典值
    * */
    @RequestMapping("/settings/dictionary/value/insertDicValue.do")
    public @ResponseBody Object insertDicValue(TblDicValue tblDicValue){
        ReturnObject returnObject = new ReturnObject();
        tblDicValue.setId(UUIDUtils.getUUID());

        int i = tblDicValueService.insert(tblDicValue);

        if(i == 1){
            //添加成功
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
        }else{
            //添加失败
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
        }

        return returnObject;
    }

    /*
    * 跳转 编辑 字典值界面
    * */
    @RequestMapping("/settings/dictionary/value/toEdit.do")
    public String toEdit(String id,HttpServletRequest request) {
        TblDicValue tblDicValue = tblDicValueService.selectByPrimaryKey(id);
        request.setAttribute("dicValue", tblDicValue);

        return "settings/dictionary/value/edit";
    }

    /*
    * 修改 字典值
    * */
    @RequestMapping("/settings/dictionary/value/updataDicValue.do")
    public @ResponseBody Object updataDicValue(TblDicValue tblDicValue){
        ReturnObject returnObject = new ReturnObject();

        int i = tblDicValueService.updateByPrimaryKeySelective(tblDicValue);

        if(i == 1){
            //修改成功
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
        }else{
            //修改失败
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
        }

        return returnObject;
    }
    /*
    * settings/dictionary/value/deleteBatchType.do"
    * 删除一批 字典值
    * */
    @RequestMapping("/settings/dictionary/value/deleteBatchValue.do")
    public @ResponseBody
    Object deleteBatchValue(@RequestParam("ids[]") String[] ids) {
        ReturnObject returnObject = new ReturnObject();


        int i = tblDicValueService.deleteBatchById(ids);

        if (i > 0) {
            //表示删除成功
            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            //表示失败
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
        }

        return returnObject;

    }




}