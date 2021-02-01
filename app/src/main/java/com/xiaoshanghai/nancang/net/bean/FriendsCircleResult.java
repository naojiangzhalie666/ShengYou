package com.xiaoshanghai.nancang.net.bean;

import java.util.List;

public class FriendsCircleResult extends BaseResult {
    /**
     * id : 1283606707725090817
     * topicContent : C-BLOCK来自湖南长沙 ，2007年组成，至10年组员正式确定为3名（大傻，key，小胖），一直以风趣幽默，贴近生活，美化世界的态度做着独立，有爱，有劲的hiphop音乐，早期以方言说唱成为本地年轻人最为热爱的团体，如今在12年加入sup music厂牌后，加上与来自香港hiphop制作人萧启道，乐团正在努力做出更多好听又具有特色的中文说唱，带来更多全新视听效果的现场。于10月2日与厂牌所有艺人推出首张合集《SUPerTape》，2014年推出最新专辑《爆出口》，其代表作《长沙策长沙》《十年长沙》《就是咯里》《你要哦改咯》《七里八里》。
     * userId : 1277072487926108161
     * topicStatus : 1
     * showDate : 07-16
     * createTime : 2020-07-16 11:37:42
     * active : 1
     * userName : Chris
     * userSex : 0
     * userPictureUrl : http://192.168.0.12:8080/imgs/portrait/7a01da39-a12d-41b8-9d12-0981159b9f82
     * userBirthday : 2002-01-01 00:00:00
     * showYear : 1
     * pictureList : ["http://192.168.0.12:8080/imgs/topic/daea541d-70bb-491b-b700-de683252ebd0","http://192.168.0.12:8080/imgs/topic/a77cb13b-babe-4a38-9a2e-d61c8d9b13f8","http://192.168.0.12:8080/imgs/topic/7fe87fb2-7b6b-4d26-9e9c-0474cbb1397d","http://192.168.0.12:8080/imgs/topic/06c7c531-504c-488d-b4a6-b50c34122503","http://192.168.0.12:8080/imgs/topic/f5005c39-25f5-49c4-ba58-751adbb339c4"]
     * likeNumber : 1
     * commentNumber : 1
     * hasLike : 0
     */

    private String id;
    private String topicContent;
    private String userId;
    private int topicStatus;
    private String showDate;
    private String createTime;
    private int active;
    private String userName;
    private String userSex;
    private String userPictureUrl;
    private String userBirthday;
    private int showYear;
    private int likeNumber;
    private int commentNumber;
    private int hasLike;
    private List<String> pictureList;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTopicStatus() {
        return topicStatus;
    }

    public void setTopicStatus(int topicStatus) {
        this.topicStatus = topicStatus;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    public void setUserPictureUrl(String userPictureUrl) {
        this.userPictureUrl = userPictureUrl;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public int getShowYear() {
        return showYear;
    }

    public void setShowYear(int showYear) {
        this.showYear = showYear;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public int getHasLike() {
        return hasLike;
    }

    public void setHasLike(int hasLike) {
        this.hasLike = hasLike;
    }

    public List<String> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
    }

}
