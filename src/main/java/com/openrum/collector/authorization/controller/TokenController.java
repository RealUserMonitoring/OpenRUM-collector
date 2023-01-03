package com.openrum.collector.authorization.controller;

import com.openrum.collector.authorization.domain.User;
import com.openrum.collector.authorization.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: Token
 * @author: lou renzheng
 * @create: 2022-12-29 11:45
 **/
@RestController
@RequestMapping("token")
public class TokenController {

    @Value("${Login.username}")
    private String realUsername;

    @Value("${Login.password}")
    private String realPassword;

    @GetMapping("getToken")
    public String login(String username, String password) {
        if (username.equals(realUsername) && password.equals(realPassword)) {

            return JWTUtils.getToken(new User(username,password));
        }
        return "authentication failed!Wrong account or password!";
    }
}
