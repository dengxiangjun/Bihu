package com.jd.dxj.model;

/**
 * description 关注的人
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/20 20:39
 **/
public class Follow {

    private String userId;

    private String userName;

    private String userIcon;

    private Long follows;

    private Long fans;

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

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public Long getFollows() {
        return follows;
    }

    public void setFollows(Long follows) {
        this.follows = follows;
    }

    public Long getFans() {
        return fans;
    }

    public void setFans(Long fans) {
        this.fans = fans;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", follows=" + follows +
                ", fans=" + fans +
                '}';
    }
}
