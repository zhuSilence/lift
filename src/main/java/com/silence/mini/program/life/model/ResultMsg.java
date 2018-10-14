package com.silence.mini.program.life.model;


/**
 * <br>
 * <b>功能：</b><br>
 * <b>作者：</b>@author Silence<br>
 * <b>日期：</b>2018-06-17 14:40<br>
 * <b>详细说明：</b>无<br>
 */
public class ResultMsg {

    private Boolean success;
    private String code;
    private String msg;
    private Object data;

    private ResultMsg() {
    }

    public ResultMsg(Boolean success) {
        this.success = success;
    }

    public ResultMsg(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public ResultMsg(Boolean success, Object data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
