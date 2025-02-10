package com.amumu.springbootdemo.service;

import com.amumu.springbootdemo.pojo.User;
import com.amumu.springbootdemo.pojo.dto.UserDto;

public interface IUserService {
    /**
     * 插入用户
     *
     * @param user 参数
     * @return
     */
    User add(UserDto user);

    /**
     * 查询用户
     * @param userId
     * @return
     */
    User getUser(Integer userId);

    /**
     * 更新用户
     * @param user
     * @return
     */
    User updateUser(UserDto user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    void deleteUser(Integer userId);
}
