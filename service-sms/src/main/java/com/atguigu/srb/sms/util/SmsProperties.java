package com.atguigu.srb.sms.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 吴浩远
 * @date 2022/5/12 21:20
 * @Description
 *
 *     account-sid: 8aaf0708809721d00180b852b6b008ff
 *     auth-token: d19006ceab974b30b217f62b9d5d630b
 *     app-id: 8aaf0708809721d00180b852b79a0906
 *     rest-url: https://app.cloopen.com:8883
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "app.cloopen")
public class SmsProperties implements InitializingBean {
    private String accountSid;
    private String authToken;
    private String appId;
    private String restUrl;

    public static String ACCOUNT_SID;
    public static String AUTH_TOKEN;
    public static String APP_ID;
    public static String REST_URL;

    /**
     * 当属性值被SpringBoot初始化完成之后调用此方法
     * */
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCOUNT_SID = accountSid;
        AUTH_TOKEN = authToken;
        APP_ID = appId;
        REST_URL = restUrl;
        log.info("容联云配置赋值完成");
    }
}
