package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contents.content;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.MyUtil;

import com.bjpowernode.crm.settings.domain.User;

import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * 齐斌
 * 2021/5/17
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /*
     *   首页面跳转
     *   验证是否选中十天免登入
     * */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(HttpServletRequest request, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        String loginAct = null;
        String loginPwd = null;

        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals("loginAct")) {
                loginAct = cookie.getValue();
            }

            if (name.equals("loginPwd")) {
                loginPwd = cookie.getValue();
            }
        }

        if (loginAct != null && loginPwd != null) {

            User user = MyUtil.getUserByAxtAndPwd(loginAct, loginPwd, userService);

            if (user != null) {

                session.setAttribute(content.SESSION_USER, user);
                //存放到session中，就相当于拿啦令牌，在此从定向session中的user可以帮助通过过滤器

                return "redirect:/workbench/index.do";
            } else {
                return "settings/qx/user/login";
            }

        }

        return "settings/qx/user/login";
    }

    /*
     * user登入
     * */
    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody
    Object login(String loginAct, String loginPwd, String checked, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        ReturnObject returnObject = new ReturnObject();
        User user = MyUtil.getUserByAxtAndPwd(loginAct, loginPwd, userService);
        /*
            封装在MyUtil中getUserByAxtAndPwd方法
        Map<String, Object> map = new HashMap<>();

        map.put("loginAct", loginAct);
        map.put("loginPwd", MD5Util.getMD5(loginPwd));

        User user = userService.selectUserByLoginActAndPwd(map);
        */

        if (user == null) {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        } else if (DateUtils.formatDateTime(new Date()).compareTo(user.getExpireTime()) > 0) {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("账号已经过期");
        } else if ("0".equals(user.getLockState())) {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("账号已被锁定");
        } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
            returnObject.setCondition(content.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("ip受限");
        } else {
            //登入成功

            returnObject.setCondition(content.RETURN_OBJECT_CODE_SUCCESS);
            //session中存进一个user 用于判断是否登入的依据
            session.setAttribute(content.SESSION_USER, user);

            if ("true".equals(checked)) {
                //选中十天免登入
                //创建cookie 设置时间10天
                Cookie cookieAct = new Cookie("loginAct", loginAct);
                cookieAct.setMaxAge(10 * 24 * 60 * 60);
                response.addCookie(cookieAct);

                Cookie cookiePwd = new Cookie("loginPwd", loginPwd);
                cookiePwd.setMaxAge(10 * 24 * 60 * 60);
                response.addCookie(cookiePwd);

            } else {
                //没选中十天免登入
                //清空cookie
                MyUtil.emptyCookie(response);
            }

        }

        return returnObject;
    }

    /*
     * 退出界面
     * */
    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response, HttpSession session) {
        //清空cookie
        MyUtil.emptyCookie(response);
        //销毁session
        session.invalidate();
        return "settings/qx/user/login";
    }

}
