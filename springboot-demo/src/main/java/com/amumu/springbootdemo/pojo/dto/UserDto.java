package com.amumu.springbootdemo.pojo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserDto {
    private Integer id;
    @NotBlank(message = "用户名不能为空") // 去除空格
    private String userMame;
    @NotBlank(message = "密码不能为空") // 去除空格
    @Length(min = 6,max = 12)
    private String password;
    @Email(message = "email格式不正确")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserMame() {
        return userMame;
    }

    public void setUserMame(String userMame) {
        this.userMame = userMame;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userMame='" + userMame + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
