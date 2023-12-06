package com.gdufe.libsys.utils;

public class ResultInfo {
    private Integer code = 200;
    private String msg = "success";

    private Object result;

    public ResultInfo() {
    }

    public ResultInfo(Integer code) {
        this.code = code;
    }

    public ResultInfo(Integer code, Object result) {
        this.code = code;
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}