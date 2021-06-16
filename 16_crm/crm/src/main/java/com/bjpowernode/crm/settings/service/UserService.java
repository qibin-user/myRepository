package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 齐斌
 * 2021/5/17
 */
public interface UserService {

    User selectUserByLoginActAndPwd(Map<String,Object> map);

    List<User> selectAllUsers();
}
