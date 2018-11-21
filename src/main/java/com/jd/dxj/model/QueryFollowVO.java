package com.jd.dxj.model;

/**
 * description QueryFollowVO
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/20 20:28
 **/
public class QueryFollowVO {
    private String queryUserId;
    private int pageNum;

    public QueryFollowVO(String userId) {
        this.queryUserId = userId;
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

    public QueryFollowVO(String queryUserId, int pageNum) {
        this.queryUserId = queryUserId;
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "QueryFollowVO{" +
                "queryUserId='" + queryUserId + '\'' +
                ", pageNum=" + pageNum +
                '}';
    }
}
