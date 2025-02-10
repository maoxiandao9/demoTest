package com.amumu.springbootdemo.controller;

import com.amumu.springbootdemo.pojo.ResponseMessage;
import com.amumu.springbootdemo.pojo.User;
import com.amumu.springbootdemo.pojo.dto.UserDto;
import com.amumu.springbootdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController  // 接口方法返回对象 转换成json文本
@RequestMapping("/user")  // localhost:8080/user/**
public class UserController {

    @Autowired
    private IUserService userService;

    // REST
    // 增加
    @PostMapping   // url: localhost:8080/user   method: post
    public ResponseMessage<User> add(@Validated @RequestBody UserDto user) {
        User userNew = userService.add(user);
        return ResponseMessage.success(userNew);
    }

    // 查询
    @GetMapping("/{userId}")  // url: localhost:8080/user/1   method: get
    public ResponseMessage<User> getUser(@PathVariable Integer userId) {
        User userNew = userService.getUser(userId);
        return ResponseMessage.success(userNew);
    }

    // 修改
    @PutMapping
    public ResponseMessage<User> updateUser(@Validated @RequestBody UserDto user) {
        User userNew = userService.updateUser(user);
        return ResponseMessage.success(userNew);
    }

    // 删除
    @DeleteMapping("/{userId}")
    public ResponseMessage<User> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseMessage.success();
    }
}
