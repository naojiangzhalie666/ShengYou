package com.xiaoshanghai.nancang.net.bean;

public class LogonResult extends BaseResult{

    /**
     * status : 1
     * token : 7880732b7dc737c71b581fc82852f6cb00479320c89975b3f9d6407104f508f0_10001
     * user : {"id":"10001","userNumber":8888888,"userName":"袁正宜","userPhone":"18811398512","userPassword":"","wechatOpenid":null,"userSalt":"","userBirthday":"2000-06-20 00:00:00","userSex":1,"userPicture":"http://39.101.164.232/imgs/user/1001.png","userIntroduce":"简单介绍：我是袁正宜","userVoice":"http://39.101.164.232/voices/user/1001.mp3","userStatus":1,"userType":1,"createTime":"2020-06-20 16:16:54","active":1,"isClanElder":0,"openTeenager":0}
     */

    private String status;
    private String token;
    private UserBean user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }


}
