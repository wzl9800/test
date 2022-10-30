package com.bjpowernode.service;

import com.bjpowernode.entity.Admin;

/**
 * @author wangzhilong
 * @dcreate 2021-11-03 19:07
 */
public interface AdminService {
    //完成登录判断
    Admin login(String name,String pwd);
}

