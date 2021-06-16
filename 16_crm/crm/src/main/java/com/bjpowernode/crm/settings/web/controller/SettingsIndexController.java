package com.bjpowernode.crm.settings.web.controller;


import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class SettingsIndexController {
    @Autowired
    private UserService userService;

    //跳转到系统设置
    @RequestMapping("/settings/index.do")
    public String index(HttpSession session) {
        return "settings/index";
    }

}
