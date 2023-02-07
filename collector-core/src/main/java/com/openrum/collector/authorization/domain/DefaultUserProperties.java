package com.openrum.collector.authorization.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: lou renzheng
 * @create: 2023-01-03 18:12
 **/
@Component
@ConfigurationProperties(prefix = "login")
@Data
public class DefaultUserProperties {

    private String username;


    private String password;

    public boolean check(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
