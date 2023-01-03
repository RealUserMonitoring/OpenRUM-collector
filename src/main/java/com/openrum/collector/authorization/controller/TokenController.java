package com.openrum.collector.authorization.controller;

import com.openrum.collector.authorization.domain.DefaultUserProperties;
import com.openrum.collector.authorization.domain.User;
import com.openrum.collector.authorization.utils.JWTUtils;
import com.openrum.collector.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${login.username}")
    private String realUsername;

    @Value("${login.password}")
    private String realPassword;

    @Autowired
    private DefaultUserProperties defaultUserProperties;

    @GetMapping("getToken")
    public Result getToken(String username, String password) {

        if (!defaultUserProperties.check(username,password)) {
            return Result.error("authentication failed!Wrong account or password!");
        }

        return Result.success(JWTUtils.getToken(new User(username,password)));


    }
}
