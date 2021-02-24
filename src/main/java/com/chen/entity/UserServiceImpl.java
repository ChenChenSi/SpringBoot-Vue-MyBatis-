package com.chen.entity;

import com.chen.dao.UserDAO;
import com.chen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
    // 1、这个其实也是一个工具对象，通过它来调用业务方法
    // 2、业务方法，其实是写在UserMapper.xml里
    // 3、UserDAO通过@Mapper、UserMapper.xml通过namespace的指向，来使两者连系在一起
    // 4、UserMapper通过id这个属性，来分别对应UserDAO接口的方法
    // 5、实际上，UserMapper是UserDAO里方法的方法体，是方法的实现
    // 6、这其实就是设计模式中（依赖倒转原则）的一个体现

    // ps：依赖倒转原则：
    // 1. 高层模块不应依赖底层模块，二者应该依赖其抽象。
    // 2. 抽象不应该依赖细节，细节应该依赖抽象
    // 3. 此原则的中心是：面向接口编程
    // 4. 此原则的设计理念：相对细节的多变性，抽象的东西要稳定的多；
    //                   以抽象为基础搭建的架构，比以细节为基础的架构要稳定的多
    // 5. 使用接口或抽象类的目的是制定好规范，而不涉及任何细节，
    //    把展现细节的任务交给他们的实现类去完成
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

    @Override
    public User login(User user) {
        // 1. 根据用户输入的用户名进行查询
        User userDB = userDAO.findByUserName(user.getUsername());
        if(!ObjectUtils.isEmpty(userDB)){
            // 2. 比较密码
            if(userDB.getPassword().equals(user.getPassword())){
                return userDB;
            }else{
                throw new RuntimeException("密码错误");
            }
        }else {
            throw new RuntimeException("用户名输入错误");
        }


    }
}
