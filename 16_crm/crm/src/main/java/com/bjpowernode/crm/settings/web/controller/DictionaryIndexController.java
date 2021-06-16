package com.bjpowernode.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 齐斌
 * 2021/5/22
 */
@Controller
public class DictionaryIndexController {


    /*
     * 跳转到字典界面
     * */
    @RequestMapping("/settings/dictionary/index.do")
    public String toDicindex(){
        return "settings/dictionary/index";

    }

}
