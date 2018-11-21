package com.jd.dxj.model;

/**
 * description 长文点赞请求
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/21 15:24
 **/
public class ContentUpVote {

    private String artId;

    private String challenge;

    private int crash;

    public ContentUpVote(String artId, String challenge, int crash) {
        this.artId = artId;
        this.challenge = challenge;
        this.crash = crash;
    }

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {
        this.artId = artId;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public int getCrash() {
        return crash;
    }

    public void setCrash(int crash) {
        this.crash = crash;
    }

    @Override
    public String toString() {
        return "ContentUpVote{" +
                "artId='" + artId + '\'' +
                ", challenge='" + challenge + '\'' +
                ", crash=" + crash +
                '}';
    }
}
