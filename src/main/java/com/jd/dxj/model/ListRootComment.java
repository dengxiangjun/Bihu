package com.jd.dxj.model;

/**
 * description 查询文章的评论
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/21 10:27
 **/
public class ListRootComment {

    private long articleId;

    private int pageNum;

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public ListRootComment(long articleId, int pageNum) {
        this.articleId = articleId;
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "ListRootComment{" +
                "articleId='" + articleId + '\'' +
                ", pageNum=" + pageNum +
                '}';
    }
}
