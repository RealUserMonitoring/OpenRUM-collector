package com.openrum.collector.authorization.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.openrum.collector.authorization.exception.AuthorizationException;
import com.openrum.collector.authorization.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: JWTInterceptor
 * @author: lou renzheng
 * @create: 2022-12-29 11:37
 **/
@Slf4j
@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Value("${login.password}")
    private String realPassword;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthorizationException {
        String token = request.getHeader("token");
        String msg = StringUtils.EMPTY;
        try {
            JWTUtils.verify(token,realPassword);
            return true;
        } catch (SignatureVerificationException e) {
            log.error("Invalid signature ->", e);
            msg = e.getMessage();
        } catch (TokenExpiredException e) {
            log.error("token Expired ->", e);
            msg = e.getMessage();
        } catch (AlgorithmMismatchException e) {
            log.error("token Inconsistent algorithm ->", e);
            msg = e.getMessage();
        } catch (Exception e) {
            log.error("invalid token ->", e);
            msg = e.getMessage();
        }
        if(StringUtils.isNotBlank(msg)){
            throw new AuthorizationException(msg);
        }

        return false;
    }
}
