package com.jd.dxj;

import com.jd.dxj.util.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description 主类
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/19 18:05
 **/
@SpringBootApplication
public class Application implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BihuCrack bihuCrack = SpringContext.getBean(BihuCrack.class);
        String accessToken = bihuCrack.login();
        if (accessToken != null) {
            bihuCrack.upVote(accessToken);
        } else logger.error("登录错误");
    }
}
