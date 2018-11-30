package com.jd.dxj;

import com.hankcs.hanlp.HanLP;
import com.jd.dxj.enums.BihuEnmus;
import com.jd.dxj.model.Follow;
import com.jd.dxj.model.UserContent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * description 测试类
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/22 12:44
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrackTest {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Resource
    private BihuCrack bihuCrack;

//    @Before
//    public void login(){
//        bihuCrack.login();
//    }

    /**
     * 测试短文点赞
     */
    @Test
    public void vpShortContentVote(){
        UserContent userContent = new UserContent();
        userContent.setContentId(1672168848L);
        userContent.setType(BihuEnmus.SHORT_CONTENT);
        bihuCrack.upVote(userContent, bihuCrack.judgement(userContent));
    }

    /**
     * 测试短文评论
     */
    @Test
    public void shortContentCreaterootcomment(){
        UserContent userContent = new UserContent();
        userContent.setContent("要活下来，你就必须用自己的价值去交换别人的价值，这是人类社会最核心的生存法则——价值供给。");
        userContent.setContentId(1398262348L);
        userContent.setType(BihuEnmus.SHORT_CONTENT);
        bihuCrack.createrootcomment(userContent, bihuCrack.judgement(userContent));//评论
    }

    /**
     * 测试长文点赞
     */
    @Test
    public void vpContentVote(){
        UserContent userContent = new UserContent();
        userContent.setContentId(1895245129L);
        userContent.setType(BihuEnmus.ARTICLE);
        bihuCrack.upVote(userContent, bihuCrack.judgement(userContent));
    }

    /**
     * 测试长文评论
     */
    @Test
    public void contentCreaterootcomment(){
        UserContent userContent = new UserContent();
        userContent.setContent("各位币友，现在好慌怎么办？");
        userContent.setContentId(1982148971L);
        userContent.setType(BihuEnmus.ARTICLE);
        bihuCrack.createrootcomment(userContent, bihuCrack.judgement(userContent));//评论
    }


    /**
     * 测试获取用户文章列表
     */
    @Test
    public void getUserContentList(){
        Follow follow = new Follow();
        follow.setUserId(String.valueOf(1426196361));

        List<UserContent> userContents = bihuCrack.getUserContentList(follow);
        logger.info(userContents.toString());
    }

    /**
     * 关键字提取
     */
    @Test
    public void extractKeyword(){
        String content = "章泽天是1993年在南京出生的，刚起头由于捧着奶茶敏捷走红于搜集，被大师亲近的叫做是“奶茶妹妹。”一天晚上章泽天外出，刚出门就碰上了记者，素颜涓滴是不惧镜头，朝着镜头看过来，我们才晓得奶茶妹妹素颜也是非常雅不雅观不雅观的。";
        List<String> keywordList = HanLP.extractPhrase(content, 5);
        logger.info(String.join(",",keywordList));
    }
}
