package com.openrum.collector.common.domain;

import com.openrum.collector.common.constant.Constant;
import lombok.Builder;
import lombok.Data;

/**
 * @description: result
 * @author: lou renzheng
 * @create: 2022-12-30
 **/
@Data
@Builder
public class Result<T> {

    private T data;

    private Integer code;

    private String msg;

    public static<T> Result build(Integer code,String msg,T data){
        return Result.builder().code(code).msg(msg).data(data).build();
    }

    public static<T> Result success(String msg,T data){
        return build(Constant.SUCCESS,msg,data);
    }

    public static Result success(String msg){
        return success(msg,null);
    }

    public static Result success(){
        return success("success");
    }

    public static<T> Result error(String msg,T data){
        return build(Constant.ERROR,msg,data);
    }

    public static Result error(String msg){
        return error(msg,null);
    }

    public static Result error(){
        return error("error");
    }


}
