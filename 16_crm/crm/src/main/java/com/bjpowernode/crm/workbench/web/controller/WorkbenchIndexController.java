package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contents.content;
import com.bjpowernode.crm.settings.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 齐斌
 * 2021/5/20
 */
@Controller
public class WorkbenchIndexController {



    @RequestMapping("/workbench/index.do")
    public String index(HttpSession session, HttpServletRequest request){
        User user = (User) session.getAttribute(content.SESSION_USER);
        request.setAttribute("user", user);
        return "workbench/index";
    }

}
