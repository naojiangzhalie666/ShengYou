package com.xiaoshanghai.nancang.constant;

public enum LoginStatus {

    /**
     * 状态，1成功 2账号冻结 3密码错误  4用户不存在 5验证码错误  6手机号为空 7密码为空 8验证码为空 9验证码超时
     */
    ERROR("0"),
    SUCCESS("1"),
    FREEZE("2"),
    PSDWRONG("3"),
    NOTEXIST("4"),
    CODEERROR("5"),
    NUMNOT("6"),
    PSDNOT("7"),
    CAPTCHANOT("8"),
    OUTTIME("9");

    private String mValue;

    private LoginStatus(String value) {
        this.mValue = value;
    }

    public static LoginStatus getValue(String value) {
        switch (value) {
            case "0":
                return LoginStatus.ERROR;
            case "1":
                return LoginStatus.SUCCESS;
            case "2":
                return LoginStatus.FREEZE;
            case "3":
                return LoginStatus.PSDWRONG;
            case "4":
                return LoginStatus.NOTEXIST;
            case "5":
                return LoginStatus.CODEERROR;
            case "6":
                return LoginStatus.NUMNOT;
            case "7":
                return LoginStatus.PSDNOT;
            case "8":
                return LoginStatus.CAPTCHANOT;
            case "9":
                return LoginStatus.OUTTIME;
            default:
                return null;
        }
    }

}
