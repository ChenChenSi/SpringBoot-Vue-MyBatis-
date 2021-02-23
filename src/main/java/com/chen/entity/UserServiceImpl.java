package com.chen.entity;

import com.chen.dao.UserDAO;
import com.chen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 淡
 * @version 1.0
 * @description
 * @create 2021-02-23 15:55
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;


    @Override
    public void register(User user){
        //0. 根据用户输入的用户名判断用户是否存在
        User userDB = userDAO.findByUserName(user.getUsername());
        if(userDB == null){
            //1. 生成用户状态
            user.setStatus("已激活");
            //2. 设置用户注册时间
            user.setRegisterTime(new Date());
            //3. 调用DAO
            userDAO.save(user);
        }else{
            throw new RuntimeException("用户名已存在！");
        }


    }
}
