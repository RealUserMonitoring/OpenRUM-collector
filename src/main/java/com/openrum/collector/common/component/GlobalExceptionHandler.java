package com.openrum.collector.common.component;

import com.openrum.collector.authorization.exception.AuthorizationException;
import com.openrum.collector.common.domain.Result;
import com.openrum.collector.memory.exception.MemorySafeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: GlobalExceptionHandler
 * @author: lou renzheng
 * @create: 2022-12-30
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handling custom authentication exceptions
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = AuthorizationException.class)
    public Result authorizationExceptionHandler(AuthorizationException e){
        log.error("Business exception occurred! as a result of：{}",e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * Handling custom authentication exceptions
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(value = MemorySafeException.class)
    public Result memorySafeExceptionHandler(MemorySafeException e){
        log.error("Business exception occurred! as a result of：{}",e.getMessage());
        return Result.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class})
    public Result RuntimeExceptionHandler(Exception e) {
        log.error("Business exception occurred! as a result of：{}",e.getMessage());
        return Result.error(e.getMessage());
    }

}
