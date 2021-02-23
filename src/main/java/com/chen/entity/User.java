package com.chen.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 淡
 * @version 1.0
 * @description
 * @create 2021-02-23 15:37
 */
@Data
@Accessors(chain = true)
public class User {
    private String id;
    private String username;
    private String realName;
    private String password;
    private String sex;
    private String status;
    private Date registerTime;
}
