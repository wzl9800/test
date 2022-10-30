package com.bjpowernode.service.impl;

import com.bjpowernode.entity.Admin;
import com.bjpowernode.entity.AdminExample;
import com.bjpowernode.mapper.AdminMapper;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangzhilong
 * @dcreate 2021-11-03 19:12
 */
@Service
public class AdminServiceImpl implements AdminService {

    //在业务逻辑层中，一定会有数据访问层的对象
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {

        //根据传入的用户名到DB中查找相应的对象
        //如果有条件，则一定要创建AdminExample的对象，用来封装条件
        AdminExample example = new AdminExample();
        /*
        * 如何添加条件
        * select a_id,a_name,a_pass from admin where a_name='admin';
        * */
        //添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if(list.size() > 0){
            Admin admin = list.get(0);
            //如果查询到用户，则进行密码比对，注意密码是密文
            String pass = MD5Util.getMD5(pwd);
            if(pass.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
