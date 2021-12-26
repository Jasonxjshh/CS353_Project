package com.jason.util;


import java.util.HashMap;

public class ResultUtils {
    public static Result<Object> success(Object object){
        Result<Object> r = new Result<Object>();
        r.setCode(ResultEnum.SUCCESS.getCode());
        r.setMsg(ResultEnum.SUCCESS.getMsg());
        r.setData(object);
        return r;
    }

    public static Result<Object> success(){
        return success(null);
    }

    public static Result<Object> Err(Integer code, String msg){
        Result<Object> r = new Result<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
