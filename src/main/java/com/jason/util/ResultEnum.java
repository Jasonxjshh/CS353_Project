package com.jason.util;


public enum ResultEnum {
    UNKNOWN_ERROR(-1, "Unknown Error"),
    SUCCESS(200, "Success"),
    PARTIAL_SUCCESS(300, "Partial Success"),
    Failure(400,"Failure");


    private Integer code;
    private String msg;


    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
