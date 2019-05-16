package com.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统级静态变量
 */
public class SystemConstant {

    public static Long TOKEN_JWT_LIFE_MINI_APP = 1000 * 60 * 30l;//jwt验证， 小程序token 生命周期，30分钟
    public static Long TOKEN_JWT_LIFE_APP = 1000 * 60 * 60 * 24 * 30l;//jwt验证， APP程序token 生命周期，一个月
    public static Long TOKEN_JWT_LIFE_LOCK_DEVICE = 1000 * 60 * 60 * 24 * 365l;//jwt验证， 门禁设备token 生命周期，一年

    public static Integer  SUCCESS_CODE = 1000;
    public static String  SUCCESS_MESSAGE = "成功";

    public static Integer  SYSTEM_ERROR_CODE = 2000;
    public static String  SYSTEM_ERROR_MESSAGE = "系统异常";

    public static Integer  SIGN_ERROR_CODE = 2001;
    public static String  SIGN_ERROR_MESSAGE = "签名异常";

    public static Integer  OVER_TIME_CODE = 2002;
    public static String  OVER_TIME_MESSAGE = "时间戳超时";

    public static Integer  LOGIN_ERROR_CODE = 2003;
    public static String   LOGIN_ERROR_MESSAGE = "账号密码不正确";

    /**
     * token
     */
    public static final int RESCODE_REFTOKEN_MSG = 1006;		//刷新TOKEN(有返回数据)
    public static final int RESCODE_REFTOKEN = 1007;			//刷新TOKEN

    public static final int JWT_ERRCODE_NULL = 4000;			//Token不存在
    public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
    public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过

    /**
     * JWT
     */
    public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";			//密匙




}
