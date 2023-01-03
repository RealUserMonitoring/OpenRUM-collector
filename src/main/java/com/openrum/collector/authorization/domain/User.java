package com.openrum.collector.authorization.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
*
* @description: User
*
* @author: lou renzheng
*
* @create: 2022/12/28
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private String username;


    private String password;

}
