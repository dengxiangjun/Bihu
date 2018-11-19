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
