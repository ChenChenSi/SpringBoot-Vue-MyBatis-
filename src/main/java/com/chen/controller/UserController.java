package com.chen.controller;

import com.chen.entity.User;
import com.chen.service.UserService;
import com.chen.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 淡
 * @version 1.0
 * @description
 * @create 2021-02-23 12:44
 */
@Slf4j
@RestController
@CrossOrigin//允许跨域
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    /*
    * 用来处理用户注册方法
    * 返回一个注册信息到前台
    * */
    @PostMapping("register")
    // 使用@RequestBody的目的是：为了能直接封装对象
    // Axios传数据时，是直接以JSON字符串的形式传
    // 必须要用@RequestBody，才能自动把其转换成我们要的User对象
    // HttpServletRequest: 存储了之前最近一次传给浏览器的验证码信息
    public Map<String, Object> register( @RequestBody User user,String code, HttpServletRequest request){
        log.info("用户信息：[{}]",user.toString());
        log.info("用户输入的验证码：[{}]",code);
        Map<String, Object> map = new HashMap<>();

        try {
            String key = (String) request.getServletContext().getAttribute("code");
            if(key.equalsIgnoreCase(code)){
                //1、调用业务方法
                userService.register(user);
                map.put("state",true);
                map.put("msg","提示：注册成功！");
            }else{
                throw new RuntimeException("验证码异常");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","提示："+e.getMessage());
        }
        return map;
    }





    /**
     * @description 生成验证码图片
     * <br>
     * @param request   HttpServletRequest对象
     *                  代表客户端的请求，
     *                  当客户端通过HTTP协议访问服务器时，
     *                  HTTP请求头中的所有信息都封装在这个对象中，
     *                  通过这个对象提供的方法，可以获得客户端请求的所有信息。
     * @return java.lang.String
     * @author 淡
     * @since 2021/2/23 12:47
     */
    @GetMapping("getImage")
    public String getImageCode(HttpServletRequest request) throws IOException {
        //1.使用工具类生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2.将验证码放入servletContext作用域
        request.getServletContext().setAttribute("code",code);
        //3.将图片转换成base64
        //字节数组输出流在内存中创建一个字节数组缓冲区，所有发送到输出流的数据保存在该字节数组缓冲区中。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //将得到的验证码，使用工具类生成验证码图片，并放入到字节数组缓存区
        VerifyCodeUtils.outputImage(220,60,byteArrayOutputStream,code);
        //使用spring提供的工具类，将字节缓存数组中的验证码图片流转换成Base64的形式
        //并返回给浏览器
        return "data:image/png;base64," + Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());

    }

}
