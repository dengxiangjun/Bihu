package com.jd.dxj.model;

/**
 * description 查询用户文章列表
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/21 8:31
 **/
public class QueryUserContentList {

    private String queryUserId;

    private int pageNum;

    private String challenge;

    private int crash;

    private String gtValue;

    public QueryUserContentList(String queryUserId, int pageNum, String challenge, int crash, String gtValue) {
        this.queryUserId = queryUserId;
        this.pageNum = pageNum;
        this.challenge = challenge;
        this.crash = crash;
        this.gtValue = gtValue;
    }

    public String getQueryUserId() {
        return queryUserId;
    }

    public void setQueryUserId(String queryUserId) {
        this.queryUserId = queryUserId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
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

    public String getGtValue() {
        return gtValue;
    }

    public void setGtValue(String gtValue) {
        this.gtValue = gtValue;
    }

    @Override
    public String toString() {
        return "QueryUserContentList{" +
                "queryUserId='" + queryUserId + '\'' +
                ", pageNum=" + pageNum +
                ", challenge='" + challenge + '\'' +
                ", crash=" + crash +
                ", gtValue='" + gtValue + '\'' +
                '}';
    }
}
