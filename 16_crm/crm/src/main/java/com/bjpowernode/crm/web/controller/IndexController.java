package com.bjpowernode.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    //处理欢迎文件跳转
    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
