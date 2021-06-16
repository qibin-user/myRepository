package com.bjpowernode.crm.settings.web.interceptor;

import com.bjpowernode.crm.commons.contents.content;
import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 齐斌
 * 2021/5/21
 * <p>
 * 登入拦截器
 */
public class LoginInterception implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest Request, HttpServletResponse Response, Object o) throws Exception {
        //拦截非法翻墙
        //对用户是否登入进行判断
        HttpSession session = Request.getSession();
        User user = (User) session.getAttribute(content.SESSION_USER);

        if (user == null) {
            //如果为空证明没有进行过登入
            //调回login页面
            Response.sendRedirect(Request.getContextPath());

            return false;
    }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
