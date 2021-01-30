package com.xiaoshanghai.nancang.net.bean;

public class UserPicturesResult extends BaseResult {


    /**
     * id : 2
     * userId : 10001
     * userPicture : http://192.168.0.12:8080/imgs/portrait/1e807a81-3a49-4f8a-8861-5a611e47d271
     * pictureOrder : 1
     * createTime : 2020-07-10 20:39:39
     * active : 1
     */

    private String id;                      //	图片地址（后续删除自己相册图片需要用）
    private String userId;                  //
    private String userPicture;             //	图片地址
    private int pictureOrder;               //
    private String createTime;              //
    private int active;                     //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public int getPictureOrder() {
        return pictureOrder;
    }

    public void setPictureOrder(int pictureOrder) {
        this.pictureOrder = pictureOrder;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
