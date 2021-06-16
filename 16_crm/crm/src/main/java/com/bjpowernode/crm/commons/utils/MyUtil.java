package com.bjpowernode.crm.commons.utils;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFCell;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 齐斌
 * 2021/5/20
 */
public class MyUtil {

    /*
     * 使用账号密码 查询用户并返回user
     * */
    public static User getUserByAxtAndPwd(String loginAct, String loginPwd, UserService userService) {
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", MD5Util.getMD5(loginPwd));
        return userService.selectUserByLoginActAndPwd(map);
    }

    public static void emptyCookie(HttpServletResponse response) {

        Cookie cookieAct = new Cookie("loginAct", null);
        cookieAct.setMaxAge(0);
        response.addCookie(cookieAct);

        Cookie cookiePwd = new Cookie("loginPwd", null);
        cookiePwd.setMaxAge(0);
        response.addCookie(cookiePwd);

    }

    public static String getCellValue(HSSFCell cell) {
        String ret = "";

        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                ret = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                ret = cell.getNumericCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                ret = cell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                ret = cell.getCellFormula();
                break;
            default:
                ret = "";

        }

        return ret;
    }
}
