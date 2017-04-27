package com.factory;

public class ResultCodeFactory
{
    /**
     * api返回结果类型的工厂
     */
    /**
     * 处理正常
     * */
    public static final int SUCCESS                            = 0;   // 

    /**
     * 未知错误
     */
    public static final int UNKOWN_ERROR                       = 1;

    /**
     * request_type无效
     * */
    public static final int REQUEST_TYPE_ERROR                 = 10;
    /**
     * 请求接口非法
     * */
    public static final int REQUEST_INTERFACE_ERROR            = 11;
    /**
     * 请求参数不完整
     * */
    public static final int REQUEST_DATA_IMCOMPLETE_ERROR      = 12;
    /**
     * 请求参数非法
     * */
    public static final int REQUEST_DATA_INVALID_ERROR         = 13;

    /**
     * 用户名重名
     * */
    public static final int REQUEST_USERNAME_HAS_EXIST_ERROR   = 100;
    /**
     * 用户名无法
     * */
    public static final int REQUEST_USERNAME_INVALID_ERROR     = 101;
    /**
     * 用户名不存在
     * */
    public static final int REQUEST_USERNAME_NOT_EXIST_ERROR   = 102;
    /**
     * 用户名被冻结
     * */
    public static final int REQUEST_USERNAME_FROZEN_ERROR      = 103;
    /**
     * 用户名被删除
     * */
    public static final int REQUEST_USERNAME_HAS_DELETED_ERROR = 104;
    /**
     * 用户名没有完成验证
     * */
    public static final int REQUEST_USERNAME_NOT_VERIFY_ERROR  = 105;
    /**
     * 密码错误
     * */
    public static final int REQUEST_PASSWORD_WRONG_ERROR       = 120;
    /**
     * 手机号非法
     * */
    public static final int REQUEST_PHONE_INVALID_ERROR        = 130;
    /**
     * 手机号已绑定到其他账号
     * */
    public static final int REQUEST_PHONE_HAS_BINDED_ERROR     = 131;
    /**
     * 手机号没有验证
     * */
    public static final int REQUEST_PHONE_NOT_VERIFY_ERROR     = 132;
    /**
     * 验证码错误
     * */
    public static final int REQUEST_VERIFY_CODE_WRONG_ERROR    = 140;
    /**
     * 账号id错误
     * */
    public static final int REQUEST_UID_WRONG_ERROR            = 150;
    /**
     * 账号token错误
     * */
    public static final int REQUEST_USER_TOKEN_ERROR           = 151;

    // 管理后台状态码
    public static final int REQUEST_ADMIN_SUCCESS              = 0;
    public static final int REQUEST_ADMIN_ERROR                = -1;
    public static final int REQUEST_ADMIN_NOT_LOGIN            = -4;

}
