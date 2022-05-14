package com.atguigu.srb.sms.service;

import java.util.Map;

/**
 * @author 吴浩远
 * @date 2022/5/12 21:50
 * @Description
 */
public interface SmsService {
    void send(String mobile, Map<String,Object> param);
}
