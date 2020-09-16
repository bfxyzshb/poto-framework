package com.weibo.poto.exception;

public enum BasicErrorCode implements ErrorCodeI {

    BIZ_ERROR("BIZ_ERROR" , "通用的业务逻辑错误"),

    COLA_ERROR("COLA_FRAMEWORK_ERROR" , "POTO框架错误"),

    SYS_ERROR("SYS_ERROR" , "未知的系统错误" );

    private String errCode;
    private String errDesc;

    private BasicErrorCode(String errCode, String errDesc){
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    @Override
    public String getErrCode() {
        return errCode;
    }

    @Override
    public String getErrDesc() {
        return errDesc;
    }
}