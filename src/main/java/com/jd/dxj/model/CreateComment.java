package com.jd.dxj.model;

/**
 * description 创建评论实体
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/21 9:38
 **/
public class CreateComment {

    private String articleId;

    private String challenge;

    private String content;

    private int crash;

    public CreateComment(String articleId, String challenge, String content, int crash) {
        this.articleId = articleId;
        this.challenge = challenge;
        this.content = content;
        this.crash = crash;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCrash() {
        return crash;
    }

    public void setCrash(int crash) {
        this.crash = crash;
    }

    @Override
    public String toString() {
        return "CreateComment{" +
                "articleId='" + articleId + '\'' +
                ", challenge='" + challenge + '\'' +
                ", content='" + content + '\'' +
                ", crash=" + crash +
                '}';
    }
}
