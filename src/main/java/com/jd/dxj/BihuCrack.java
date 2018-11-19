package com.jd.dxj;

import com.alibaba.fastjson.JSONObject;
import com.jd.dxj.model.User;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * description TODO
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/19 18:10
 **/
@Component
public class BihuCrack {

    private static Logger logger = LoggerFactory.getLogger(BihuCrack.class);

    @Value("${bihuHost}")
    private String host;

    @Value("${phone}")
    private String phone;

    @Value("${password}")
    private String password;

    private BasicCookieStore cookieStore;

    /**
     * 登录
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
                cookie.setComment("HttpOnly");
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 点赞
     * @param accessToken
     */
    public void upVote(String accessToken) {
        User upVoteUser = new User("abc5060befd93ce5c74726a724bf1ac6", "1");
        HttpClient client = new DefaultHttpClient();
        String url = host + "/bihu/shortContent/1863020483/upVote";
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity(JSONObject.toJSONString(upVoteUser));
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
           ((DefaultHttpClient) client).setCookieStore(cookieStore);

            post.setHeader("Accept","*/*");
            post.setHeader("Accept-Encoding","gzip, deflate, br");
            post.setHeader("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
            post.setHeader("Authorization","token " + accessToken);
            post.setHeader("Connection","keep-alive");
            //post.setHeader("Content-Length","58");
            post.setHeader("Content-Type","application/json;charset=UTF-8");
            post.setHeader("Host","be02.bihu.com");
            post.setHeader("Origin","https://bihu.com");
            post.setHeader("Referer","https://bihu.com/shortContent/1863020483");
            post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            HttpResponse httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(new GzipDecompressingEntity(httpResponse.getEntity()));
                logger.info(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
