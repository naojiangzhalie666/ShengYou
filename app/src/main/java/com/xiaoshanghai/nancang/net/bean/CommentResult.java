package com.xiaoshanghai.nancang.net.bean;

import java.util.List;

public class CommentResult {


    /**
     * id : 5
     * topicId : 1277153509330092034
     * userId : 1277090083920588802
     * userName : garden
     * userPictureUrl : http://192.168.0.12:8080/imgs/portrait/1e807a81-3a49-4f8a-8861-5a611e47d271
     * commentContent : 哼，骗人的
     * commentType : 1
     * fatherId : null
     * showDate : 06-28
     * createTime : 2020-06-28 18:29:44
     * active : 1
     * sonComments : []
     */

    private String id;
    private String topicId;
    private String userId;
    private String userName;
    private String userPictureUrl;
    private String commentContent;
    private int commentType;
    private String fatherId;
    private String showDate;
    private String createTime;
    private int active;
    private List<CommentResult> sonComments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    public void setUserPictureUrl(String userPictureUrl) {
        this.userPictureUrl = userPictureUrl;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
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

    public List<CommentResult> getSonComments() {
        return sonComments;
    }

    public void setSonComments(List<CommentResult> sonComments) {
        this.sonComments = sonComments;
    }
}
