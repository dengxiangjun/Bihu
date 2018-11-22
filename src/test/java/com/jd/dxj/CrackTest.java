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

    @Before
    public void login(){
        bihuCrack.login();
    }

    /**
     * 测试短文点赞
     */
    @Test
    public void vpShortContentVote(){
        UserContent userContent = new UserContent();
        userContent.setContentId(1225146837L);
        userContent.setType(1);
        bihuCrack.upVote(userContent, bihuCrack.judgement());
    }

    /**
     * 测试短文评论
     */
    @Test
    public void shortContentCreaterootcomment(){
        UserContent userContent = new UserContent();
        userContent.setSnapcontent("");
        userContent.setContentId(1225146837L);
        userContent.setType(1);
        bihuCrack.createrootcomment(userContent, bihuCrack.judgement());//评论
    }

    /**
     * 测试长文点赞
     */
    @Test
    public void vpContentVote(){
        UserContent userContent = new UserContent();
        userContent.setContentId(1608776331L);
        userContent.setType(BihuEnmus.SHORT_CONTENT);
        bihuCrack.upVote(userContent, bihuCrack.judgement());
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
        bihuCrack.createrootcomment(userContent, bihuCrack.judgement());//评论
    }


    /**
     * 测试获取用户文章列表
     */
    @Test
    public void getUserContentList(){
        Follow follow = new Follow();
        follow.setUserId(String.valueOf(271920));

        List<UserContent> userContents = bihuCrack.getUserContentList(follow);
        logger.info(userContents.toString());
    }

    /**
     * 关键字提取
     */
    @Test
    public void extractKeyword(){
        String content = "1.做个行者，不当观光客。\n" +
                "\n" +
                "观光客只会选择最容易的大众路线，而行者则追求未知的挑战。观光客只肯乖乖地守着大巴车，而行者有勇气跳进泥堆去感受生活。\n" +
                "\n" +
                "2.永远做自己。\n" +
                "\n" +
                "读大学的时候，我和一位朋友去亚洲背包游。因为朋友不肯换掉他的跨栏背心和人字拖，很多地方都把我们拒之门外。二十年过去了，他依然穿着人字拖！这成了他的人生哲学。不管做什么，都要做真实的自己。不管去哪儿，都别忘了自己的人字拖。\n" +
                "\n" +
                "3.忽略挡路的墙，放飞梦想。\n" +
                "\n" +
                "当你遇到阻碍的时候，找个办法翻过去，绕过去，甚至是从底下钻过去。不管怎样，不要投降。不要再一堵墙面前就屈服了。\n" +
                "\n" +
                "4.给老朋友留个位置。";
        List<String> keywordList = HanLP.extractSummary(content, 5);
        logger.info(String.join(",",keywordList));
    }
}
