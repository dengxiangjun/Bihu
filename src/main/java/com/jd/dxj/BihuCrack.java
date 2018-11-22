package com.jd.dxj;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.jd.dxj.enums.BihuEnmus;
import com.jd.dxj.mapper.FollowMapper;
import com.jd.dxj.model.*;
import com.jd.dxj.util.MailUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * description 币乎破解
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/19 18:10
 **/
@Component
public class BihuCrack {

    private static User currentUser;

    private static Logger logger = LoggerFactory.getLogger(BihuCrack.class);

    @Value("${bihuHost}")
    private String host;

    @Value("${phone}")
    private String phone;

    @Value("${password}")
    private String password;

    private BasicCookieStore cookieStore;

    @Resource
    private FollowMapper followMapper;


    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean

    @Value("${spring.mail.username}")
    private String sender; //读取配置文件中的参数

    @Value("${mailTo}")
    private String mailTo; //读取配置文件中的参数

    private String[] commentArr = {"唉，老铁稳！", "支持好文赞一个",
            "稳当", "来了！", "大佬好，很棒", "整理的很棒", "文章内容非常好", "后排真爱粉来了", "精彩",
            "嗯嗯，文章质量非常高，感谢您的分享", "希望能有所借鉴", "好文，能量送上", "你的前排太难抢了，我费了好大的劲啊", "值得学习", "准时到，好文章不怕晚，默默期待",
            "新人每天都来关注币虎的数据，真的帮助很大", "谢谢分享支持币乎榜", "准时来支持看榜单数据", "来学习新文章支持币乎", "每日拜读，受益匪浅！", "币乎权威数据，很全面",
            "这文章真不错", "每天定时来学习", "好文要支持", "支持老友",
            "文章不错，支持你", "大神力作，支持", "如果你心里不认可数字货币，不要做梦能发财，你肯定拿不住的，先想办法从心里认可区块链，认可你持有的数字货币再说。",
            "感谢分享，学习了", "打卡"};

    /**
     * 登录
     *
     * @return
     */
    public String login() {
        User user = new User(phone, password, "abc5060befd93ce5c74726a724bf1ac6", "1");
        HttpClient client = new DefaultHttpClient();
        String url = host + "/api/user/loginViaPassword";
        HttpPost post = new HttpPost(url);
        String token = null;
        try {
            StringEntity s = new StringEntity(JSONObject.toJSONString(user));
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);

            HttpResponse httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String cookieStr = httpResponse.getFirstHeader("Set-Cookie").getValue();

                String[] arr = cookieStr.split("; ");
                cookieStore = new BasicCookieStore();
                logger.info(arr[0].substring("aliyungf_tc=".length()));
                BasicClientCookie cookie = new BasicClientCookie("aliyungf_tc", arr[0].substring("aliyungf_tc=".length()));
                //cookie.setComment("HttpOnly");
                cookie.setPath("/");
                cookieStore.addCookie(cookie);
                logger.info(cookieStr);
//                Header[] headers = httpResponse.getAllHeaders();
//                for (Header header : headers) {
//                    System.out.println("Key : " + header.getName()
//                            + " ,Value : " + header.getValue());
//                }

                String content = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
                logger.info(content);
                int res = jsonObject.getInteger("res");
                if (res == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    token = data.getString("accessToken");
                    String userId = data.getString("userId");
                    String memberId = data.getString("memberId");
                    currentUser = new User(phone, password, "", "1", userId, memberId, token);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    public String judgement() {
        String challenge = null;

        HttpClient client = new DefaultHttpClient();
        String url = "https://api.geetest.com/gt_judgement?pt=0&gt=a5df0ab51a6222365f92fb01662fd57e";
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity("1tT)OF(gPlrn5GHNeoglZMTua6Im)BOwW82gk)OU9QEorTkcUHjEHpQ44e(vlebS3ZiDjkLXkGzBGESp9yjQZ)HgniCGlcnZU1RWG9gGyBoqWNIQR5ODGd)lYxPBpaHUAMWgIk)VURyTJg9JfW4nue49A26pVXw5p2qo)jphBJkJH9V1bI7uSwYNL92)xYXIDLcKd)BdiGNPJplOnsAqmVLK6ONWmMqI0UgT8brG8ZYLh9OMGTxiywxTYtBnUhWVjJsBo48WMHhvinReA0xXG8WXcb6EV8OItknwF(TOYmPiPLEsNX4(atVyljI8rkT78ERMJxRarN5y)kVc)Re2s6ywbfNlaqccpVWKZzC8oGoBgIAU1xFj6OBJ75nWY3U1i)v6wYB5FjxRwSiMb3ym31j)gZF8hzdGlZa1IIrP)vKnG4CHoWRMB4mKEI3uNRLuYAt5c7jRfGG0FC6KY5CvZX2aVewnfXYdPOAvze2ZDvv7wOenvQFNcIYt1YleiXf7mkwEB0Lc9ACjc)ihSK0eoR5Vnu7HdIyNAroXqQV5AlIhJA5TCIGcg8Hj8yBlt6V6jccKoesn23JCK1Huhtt7nQWWCIM7u5AICzgnUWaeHVsV8I2oQNPTD4AgdKQUPpBG1GF9)gnrKaajMTCfMfiF4RETRGTJDHaiB36QgZwYHHD0jNfv(MUSwz55UkyPReYm1e7FGNI7MdroonnOmkHXEE2DZH5FyIpG4HmmSuM)tpv37gyvSDhb4A9Y2k6YtLoAPcYlLHwN90jwJWrizOUlqFTrdum)zLWuas)mwt9rLM0p(T5)71ZTa89HLBcSFAJa3DpXaRkV)qrauQqYuwaLGS6vLVxwfiryagWfu9KFG7K6Y9ONGweh9LAuIgfhYgjTUn8zIPPHas1KAzSq5yXL1EqDxvzNAlvUxUFeh3zRQ7oax5y0eifC2j0iSmW1bC2EUfObCPV6zICnO74TVveGTnH(jFC3mCnA1mjoDQYDzooTzBejQPADxrQ60S5i2pwsA2qaeZ4GauLmp8PAQ9QnTZBbgI05EVO8JGjOPQl4CJnn(D4kX4QcmqFmQa6R(HniBjBkASTUACD8kpY7jLmNks08wcjRWyErWNoHpDvd4q)Aviw0ey8NeSHggafAXog0TXP3uvobrDF9wY9pFp5jJ6boxhAVLVCE4an)RyltUJB81GN(0UHsAY1fp5(Dwxl8ylBSRBqLT)8XnWR0CZDEDzaSvJaEzwWTqcrXC664MChBIVIOKCcKNQGXAaBqhqC5jLZJ5oIizTKzAloKevtZPrKOdhIK800Rpi4uShEGdKFvUkzh8KRmYtNDohXsa4M740gPxL9f(v3ChOyebPE74GZ86R3YtzuZLcYk1sN672xB5bgE8aNktnX(K8naSC(GiRqNyS5G1JM8angRgDaN5relFErQxydJzYM)MXrc4zvyekg9bPBC0ck6pga4wDWpeFhzpbMUEE6CYrq6MFUNyYQycnE4stOAN8nxrfUKoSH719Ph)7v3syRsg4O5S0hAxwBQj6X5qV6D0ceJ5acN9Xn14vby)Kv8p3PIWISTTEZ)6WDY4cbOGViEcIVtZA51dPJy7rPrHn0F1Y6QI5UjaLQEiHcy1Eam6K(cox3sT9F1UreM6qhqPNfP9LyViZol8xRy8dk1uV3HzVkffwy3SeUxe9Tc3veaiNUToAq5FzMIW3qrMN)thd1FS2hH8AYG)88mtsfJNOgMF1FCpiNU3qukj5uMZOTsPVTYr41XEpVMA0OUGwj3GTHxglcISla7OlUe(Q(1XXG0MCuvKqaSneqUVms7ADSpfjTJr65lPYFoJrM2nNz1ng211eVpBWnbb)PN(uC70)H0eRvUIYhG4huCBpWSjpqIHfM3fb80x3r51IIPOsOClHXyEoFYuQXicfLpPAk6I(R1ByHXnwE40hXYAoP7CGKn7GMlkvlS(9drBxGfo0gmop)xYvRyiQn7tRNdloz5TZ(3Rjsycl0ItJCqvsyR)dVxRAvlXphD3vPhbHkPj0b3owQcnRROqRAQHZ1EpJuZ))glulOHUSN)mLBz2enHZSlc)tMTBfMFd0vylCp6cdBcILX6rMtDvuemoxSHm1U0BOoYT1llDTOXyv9KcVCb3lJdEmIv9WqyaOdZtqmoucFvuN1khtPvx9MYfEUFJynv0QrBHV)m20e8xK)Z7675(89bqNlW7O9ua1IjtZQpN2CqAA3hDTBJNz4jj45mxT2SKO8DBPWP)L1)B(wuMx7kChipxMm4FOfLF5hwEAeRCJKjQf0dwWHdTo37V2Hv5DA8lUUoHP34XCRSi0HxBp2n950yS6wG2DeccDRsNy7X0gbGMxfXh6MjafoM4U1CHewgruCNWrGKuIy1pyATwK)sejKJJ8YjFezNzy1nxPRInO3Jh0d5FKT29W2HKsnfToCvNs6nDS2CJ8YNyK81cg7o4m5dqRE7(WkylcHOXZqRggek)h1F5z6VoZJ(3ybuLHLA9NddcbdPZmaacvmfFBQX8jwtdnATVtUo(yJMaUZ0Kxx1FQUFc3FHb4AJdzSX4nkvQfGacUGh)Jz(Tw..56d850fddb69afafc016f7acb57de7f8322efde0721128d0ce84753a01b03b4cec8325731294d7ec24b9101f24e1d0447a586a7c42ca6aee6bb40ae1724ccae6f35dc4d41eabd5d81d41f212c0a74e0cd25d0023fe0e5d2f6cc77265d6426b061f578a3e51f915649aa457e14a6682170f610a9ca23742dda0418f99dff85f08");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);

            BasicCookieStore cookieStore1 = new BasicCookieStore();
            BasicClientCookie cookie = new BasicClientCookie("GeeTestAjaxUser", "f999319e9b40a6fbbadd674c04cecbbb");
            cookieStore1.addCookie(cookie);
            cookie = new BasicClientCookie("GeeTestUser", "9ed43777e0e082cb56d90f0197aed6af");
            cookieStore1.addCookie(cookie);
            ((DefaultHttpClient) client).setCookieStore(cookieStore1);

            post.setHeader("Accept", "*/*");
            post.setHeader("Accept-Encoding", "gzip, deflate, br");
            post.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            // post.setHeader("Authorization","token " + accessToken);
            post.setHeader("Connection", "keep-alive");
            //post.setHeader("Content-Length","58");
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            post.setHeader("Host", "api.geetest.com");
            post.setHeader("Origin", "https://bihu.com");
            post.setHeader("Referer", "https://bihu.com/shortContent/1841045007");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            HttpResponse httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(httpResponse.getEntity());
                logger.info(content);
                JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
                challenge = jsonObject.getString("challenge");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return challenge;
    }

    /**
     * 点赞
     *
     * @param userContent
     * @param challenge
     */
    public void upVote(UserContent userContent, String challenge) {
        String articleLink = concatArticleLink(userContent);
        if (userContent.getType() == BihuEnmus.SHORT_CONTENT) {//微文
            User upVoteUser = new User(challenge, "1");
            HttpClient client = new DefaultHttpClient();
            String url = host + "/bihu/shortContent/" + userContent.getContentId() + "/upVote";
            HttpPost post = new HttpPost(url);
            try {
                StringEntity s = new StringEntity(JSONObject.toJSONString(upVoteUser));
                s.setContentEncoding("UTF-8");
                s.setContentType("application/json");
                post.setEntity(s);
                ((DefaultHttpClient) client).setCookieStore(cookieStore);

                post.setHeader("Accept", "*/*");
                post.setHeader("Accept-Encoding", "gzip, deflate, br");
                post.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                post.setHeader("Authorization", "token " + currentUser.getAccessToken());
                post.setHeader("Connection", "keep-alive");
                //post.setHeader("Content-Length","58");
                post.setHeader("Content-Type", "application/json;charset=UTF-8");
                post.setHeader("Host", "be02.bihu.com");
                post.setHeader("Origin", "https://bihu.com");
                post.setHeader("Referer", "https://bihu.com/shortContent/1841045007");
                post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
                HttpResponse httpResponse = client.execute(post);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String content = EntityUtils.toString(new GzipDecompressingEntity(httpResponse.getEntity()));
                    JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
                    int res = jsonObject.getInteger("res");
                    if (res == 1) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        int income = data.getInteger("income");
                        logger.info("微文点赞成功，收益是：" + income + " ;url: " + url);
                        MailUtil.sendTxtMail(mailSender, sender, mailTo.split(","), "长文点赞成功", articleLink);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ContentUpVote contentUpVote = new ContentUpVote(String.valueOf(userContent.getContentId()), challenge, 1);
            HttpClient client = new DefaultHttpClient();
            String url = host + "/api/content/upVote";
            HttpPost post = new HttpPost(url);
            try {
                StringEntity s = new StringEntity(JSONObject.toJSONString(contentUpVote));
                s.setContentEncoding("UTF-8");
                s.setContentType("application/json");
                post.setEntity(s);
                ((DefaultHttpClient) client).setCookieStore(cookieStore);

                post.setHeader("Accept", "*/*");
                post.setHeader("Accept-Encoding", "gzip, deflate, br");
                post.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                post.setHeader("Authorization", "token " + currentUser.getAccessToken());
                post.setHeader("Connection", "keep-alive");
                //post.setHeader("Content-Length","58");
                post.setHeader("Content-Type", "application/json;charset=UTF-8");
                post.setHeader("Host", "be02.bihu.com");
                post.setHeader("Origin", "https://bihu.com");
                post.setHeader("Referer", "https://bihu.com/shortContent/1841045007");
                post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
                HttpResponse httpResponse = client.execute(post);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String content = EntityUtils.toString(new GzipDecompressingEntity(httpResponse.getEntity()));
                    JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
                    int res = jsonObject.getInteger("res");
                    if (res == 1) {
                        int data = jsonObject.getInteger("data");
                        logger.info("长文点赞成功，收益是：" + data + " ;长文信息: " + contentUpVote);
                        MailUtil.sendTxtMail(mailSender, sender, mailTo.split(","), "长文点赞成功", articleLink);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取关注列表
     */
    public List<Follow> getUserFollowList() {
        List<Follow> follows = followMapper.selectAll();
        if (follows.size() == 0) {
            follows = getUserFollowListByHttp();
            followMapper.insertList(follows);
        }

        Collections.sort(follows, new Comparator<Follow>() {
            @Override
            public int compare(Follow o1, Follow o2) {
                return -Long.compare(o1.getFans(), o2.getFans());
            }
        });
        return follows;
    }

    /**
     * 从网络上获取关注列表
     *
     * @return
     */
    public List<Follow> getUserFollowListByHttp() {
        List<Follow> follows = new ArrayList<>();
        HttpClient client = new DefaultHttpClient();
        String url = host + "/api/content/show/getUserFollowList";
        HttpPost post = new HttpPost(url);
        int pageNum = 1;
        int pageCnt = 1;
        while (pageNum <= pageCnt) {
            try {
                QueryFollowVO queryFollowVO = new QueryFollowVO(currentUser.getUserId(), pageNum++);

                StringEntity s = new StringEntity(JSONObject.toJSONString(queryFollowVO));
                s.setContentEncoding("UTF-8");
                s.setContentType("application/json");
                post.setEntity(s);

                post.setHeader("Accept", "*/*");
                post.setHeader("Accept-Encoding", "gzip, deflate, br");
                post.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                post.setHeader("Authorization", "token " + currentUser.getAccessToken());
                post.setHeader("Connection", "keep-alive");
                post.setHeader("Content-Type", "application/json;charset=UTF-8");
                post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
                HttpResponse httpResponse = client.execute(post);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String content = EntityUtils.toString(new GzipDecompressingEntity(httpResponse.getEntity()));
                    logger.info(content);
                    //follows = parseFollowList(content);
                    JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
                    JSONObject dataJO = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = dataJO.getJSONArray("list");
                    List<Follow> pageFollowList = jsonArray.toJavaList(Follow.class);
                    follows.addAll(pageFollowList);

                    int total = dataJO.getInteger("total");
                    int pageSize = dataJO.getInteger("pageSize");
                    pageCnt = total / pageSize + (total % pageSize > 0 ? 1 : 0);
                    //logger.info("pageCnt: " + pageCnt);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return follows;
    }

//    public void refrshFollow(){
//        followMapper.d
//    }

//    /**
//     * 解析关注人列表
//     *
//     * @param content
//     */
//    private List<Follow> parseFollowList(String content) {
//        JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
//        JSONObject dataJO = jsonObject.getJSONObject("data");
//        JSONArray jsonArray = dataJO.getJSONArray("list");
//        List<Follow> followList = jsonArray.toJavaList(Follow.class);
//
//
//        followMapper.insertList(followList);
//        return followList;
//    }

    /**
     * 获取用户文章列表
     *
     * @param follow
     */
    public List<UserContent> getUserContentList(Follow follow) {
        HttpClient client = new DefaultHttpClient();
        String url = host + "/bihu/user/getUserContentList";
        HttpPost post = new HttpPost(url);
        List<UserContent> userContents = new ArrayList<>();
        try {
            QueryUserContentList queryUserContentList = new QueryUserContentList(follow.getUserId(), 1,
                    "9bef5f21942000149210bf9e0ad8b9c1", 1,
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

            StringEntity s = new StringEntity(JSONObject.toJSONString(queryUserContentList));
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);

            post.setHeader("Accept", "*/*");
            post.setHeader("Accept-Encoding", "gzip, deflate, br");
            post.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            post.setHeader("Authorization", "token " + currentUser.getAccessToken());
            post.setHeader("Connection", "keep-alive");
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            HttpResponse httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(new GzipDecompressingEntity(httpResponse.getEntity()));
                logger.info("请求用户文章列表成功，follow: " + follow);
                userContents = parseUserContent(content);
            }
        } catch (IOException e) {
            logger.error("url: " + url + " ; follow" + follow);
            e.printStackTrace();
        }
        return userContents;
    }

    /**
     * 解析用户文章
     *
     * @param json
     * @return
     */
    private List<UserContent> parseUserContent(String json) {
        JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
        JSONObject dataJO = jsonObject.getJSONObject("data");
        JSONArray jsonArray = dataJO.getJSONArray("list");
        List<UserContent> userContents = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject1 = (JSONObject) object;

            int ups = jsonObject1.getInteger("ups");
            if (ups > 0) continue;//跳过已经被人点赞过得文章

            int up = jsonObject1.getInteger("up");
            if (up == 1) continue; //跳过已经点赞的文章
            Long id = jsonObject1.getLong("id");
            Long contentId = jsonObject1.getLong("contentId");
            int type = jsonObject1.getInteger("type");
            // if (type == 1) continue;
            String content = jsonObject1.getString("content");
            String snapcontent = jsonObject1.getString("snapcontent");
            Date createTime = jsonObject1.getDate("createTime");
            String userId = jsonObject1.getString("userId");
            String userName = jsonObject1.getString("userName");
            UserContent userContent = new UserContent(id, contentId, type, content, snapcontent, up, ups, createTime, userId, userName);

            if (hasComment(userContent)) continue;
            logger.info("需要点赞和评论的文章: " + userContent);
            userContents.add(userContent);

        }
        return userContents;
    }

    /**
     * 判断文章是否有人评论过
     *
     * @param userContent
     * @return
     */
    public boolean hasComment(UserContent userContent) {
        ListRootComment listRootComment = new ListRootComment(userContent.getContentId(), 1);
        HttpClient client = new DefaultHttpClient();
        String url = host + "/bihu/comment/listrootcomment";
        HttpPost post = new HttpPost(url);
        int total = 0;
        try {
            StringEntity s = new StringEntity(JSONObject.toJSONString(listRootComment));
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);

            post.setHeader("Accept", "*/*");
            post.setHeader("Accept-Encoding", "gzip, deflate, br");
            post.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            post.setHeader("Authorization", "token " + currentUser.getAccessToken());
            post.setHeader("Connection", "keep-alive");
            //post.setHeader("Content-Length","58");
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            HttpResponse httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(new GzipDecompressingEntity(httpResponse.getEntity()));
                JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
                int res = jsonObject.getInteger("res");
                if (res == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    total = data.getInteger("total");
                    //logger.info("文章: " + userContent.getContentId() + "的评论个数是: " + total);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userContent.getType() == 0 ? total > 5 : total > 0;//长文5个以上的人评论表示已经被人评论过了,微文抢第一个
    }


    /**
     * 评论
     *
     * @param userContent
     * @param challenge
     */
    public void createrootcomment(UserContent userContent, String challenge) {
        String comment = commentGenerator(userContent);
        String articleLink = concatArticleLink(userContent);

        CreateComment createComment = new CreateComment(String.valueOf(userContent.getContentId()), challenge, comment, 1);
        //CreateComment createComment = new CreateComment(String.valueOf(1750244118), challenge, "好文", 1);
        HttpClient client = new DefaultHttpClient();
        String url = host + "/bihu/comment/createrootcomment";
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity(JSONObject.toJSONString(createComment), "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            post.setHeader("Accept", "*/*");
            post.setHeader("Accept-Encoding", "gzip, deflate, br");
            post.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            post.setHeader("Authorization", "token " + currentUser.getAccessToken());
            post.setHeader("Connection", "keep-alive");
            //post.setHeader("Content-Length","58");
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            HttpResponse httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(new GzipDecompressingEntity(httpResponse.getEntity()));
                JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
                int res = jsonObject.getInteger("res");
                if (res == 1) {
                    logger.info("评论成功， ;url: " + url);

                    MailUtil.sendTxtMail(mailSender, sender, mailTo.split(","), "评论成功", articleLink);
                } else logger.info(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拼接文章链接
     * @param userContent
     * @return
     */
    private String concatArticleLink(UserContent userContent) {
        return "https://bihu.com/".concat(userContent.getType() == BihuEnmus.ARTICLE ? "article" : "shortContent")
                .concat("/" + userContent.getContentId());
    }

    /**
     * 评论生成器
     *
     * @param userContent
     * @return
     */
    private String commentGenerator(UserContent userContent) {
       // String content = userContent.getType() == BihuEnmus.ARTICLE ? userContent.getContent() : userContent.getSnapcontent();
        List<String> keywordList = HanLP.extractSummary(userContent.getContent(), 5);
        String comment;
        if (keywordList.size() > 0) comment = String.join(",", keywordList);
        else {
            Random random = new Random();
            comment = commentArr[random.nextInt(commentArr.length)];
        }
        return comment;
    }
}
