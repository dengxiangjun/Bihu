package com.jd.dxj.model;

import java.util.Date;

/**
 * description 用户文章
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/21 8:49
 **/
public class UserContent {
    private Long id;
    private Long contentId;
    private Integer type;
    private String content;
    private String snapcontent;
    private Integer up;
    private Integer ups;
    private Date createTime;
    private String userId;
    private String userName;

    public UserContent() {
    }

    public UserContent(Long id, Long contentId, Integer type, String content, String snapcontent, Integer up, Integer ups, Date createTime, String userId, String userName) {
        this.id = id;
        this.contentId = contentId;
        this.type = type;
        this.content = content;
        this.snapcontent = snapcontent;
        this.up = up;
        this.ups = ups;
        this.createTime = createTime;
        this.userId = userId;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSnapcontent() {
        return snapcontent;
    }

    public void setSnapcontent(String snapcontent) {
        this.snapcontent = snapcontent;
    }

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return "UserContent{" +
                "id=" + id +
                ", contentId=" + contentId +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", snapcontent='" + snapcontent + '\'' +
                ", up=" + up +
                ", ups=" + ups +
                ", createTime=" + createTime +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
