package com.jd.dxj.model;

/**
 * description 用户
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/19 18:19
 **/
public class User {

    private String phone;

    private String password;

    private String challenge;

    private String crash;

    private String userId;

    private String memberId;

    private String accessToken;

    /**
     * 粉丝个数
     */
    private int fansCnt;

    public User(String phone, String password, String challenge, String crash) {
        this.phone = phone;
        this.password = password;
        this.challenge = challenge;
        this.crash = crash;
    }

    public User(String challenge, String crash) {
        this.challenge = challenge;
        this.crash = crash;
    }

    public User(String phone, String password, String challenge, String crash, String userId, String memberId, String accessToken) {
        this.phone = phone;
        this.password = password;
        this.challenge = challenge;
        this.crash = crash;
        this.userId = userId;
        this.memberId = memberId;
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getCrash() {
        return crash;
    }

    public void setCrash(String crash) {
        this.crash = crash;
    }

    public int getFansCnt() {
        return fansCnt;
    }

    public void setFansCnt(int fansCnt) {
        this.fansCnt = fansCnt;
    }

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", challenge='" + challenge + '\'' +
                ", crash='" + crash + '\'' +
                '}';
    }
}
