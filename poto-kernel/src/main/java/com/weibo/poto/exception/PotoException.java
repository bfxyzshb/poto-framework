package com.weibo.poto.exception;

public class PotoException extends BaseException {
    public PotoException(String errMessage) {
        super(errMessage);
        this.setErrCode(BasicErrorCode.POTO_ERROR);
    }

    public PotoException(String errMessage, Throwable e) {
        super(errMessage, e);
        this.setErrCode(BasicErrorCode.POTO_ERROR);
    }
}
