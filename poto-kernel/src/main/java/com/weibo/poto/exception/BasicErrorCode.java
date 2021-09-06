package com.weibo.poto.exception;

public enum BasicErrorCode implements ErrorCodeI {

<<<<<<< HEAD
    BIZ_ERROR("BIZ_ERROR", "通用的业务逻辑错误"),

    POTO_ERROR("POTO_FRAMEWORK_ERROR", "POTO框架错误"),

    SYS_ERROR("SYS_ERROR", "未知的系统错误"),

    PARAM_ERROR("PARAM_ERROR", "参数错误");
=======
    BIZ_ERROR("BIZ_ERROR" , "通用的业务逻辑错误"),

    COLA_ERROR("COLA_FRAMEWORK_ERROR" , "POTO框架错误"),

    SYS_ERROR("SYS_ERROR" , "未知的系统错误" );
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b

    private String errCode;
    private String errDesc;

<<<<<<< HEAD
    private BasicErrorCode(String errCode, String errDesc) {
=======
    private BasicErrorCode(String errCode, String errDesc){
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
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