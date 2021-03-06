package com.weibo.poto.bus.command;

import com.weibo.poto.bus.common.DTO;

/**
 * command 处理结果对象
 */
public class Response<T> extends DTO {

    private static final long serialVersionUID = 1L;

    private boolean isSuccess;

    private String errCode;

    private String errMessage;

    private T data;

    public boolean isSuccess() {
        return isSuccess;
    }


    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }


    public String getErrCode() {
        return errCode;
    }


    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }


    public String getErrMessage() {
        return errMessage;
    }


    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response [isSuccess=" + isSuccess + ",data=" + data + ", errCode=" + errCode + ", errMessage=" + errMessage + "]";
    }

    public static Response buildFailure(String errCode, String errMessage) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static Response buildFailure() {
        Response response = new Response();
        response.setSuccess(false);
        return response;
    }

    public static Response buildSuccess() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static Response buildSuccess(Object data) {
        Response response = new Response();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }
}
