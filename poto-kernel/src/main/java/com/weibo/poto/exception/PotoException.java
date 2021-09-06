package com.weibo.poto.exception;

<<<<<<< HEAD
public class PotoException extends BaseException {
    public PotoException(String errMessage) {
        super(errMessage);
        this.setErrCode(BasicErrorCode.POTO_ERROR);
=======
public class PotoException extends BaseException{
    public PotoException(String errMessage) {
        super(errMessage);
        this.setErrCode(BasicErrorCode.COLA_ERROR);
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
    }

    public PotoException(String errMessage, Throwable e) {
        super(errMessage, e);
<<<<<<< HEAD
        this.setErrCode(BasicErrorCode.POTO_ERROR);
=======
        this.setErrCode(BasicErrorCode.COLA_ERROR);
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
    }
}
