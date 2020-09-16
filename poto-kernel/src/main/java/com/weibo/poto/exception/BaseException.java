package com.weibo.poto.exception;

public abstract class BaseException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
    private ErrorCodeI errCode;
    
    public BaseException(String errMessage){
        super(errMessage);
    }
    
    public BaseException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
    
    public ErrorCodeI getErrCode() {
        return errCode;
    }
    
    public void setErrCode(ErrorCodeI errCode) {
        this.errCode = errCode;
    }
    
}