package com.atguigu.srb.sms.feign.fallback;

import com.atguigu.srb.sms.feign.CoreUserInfoFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wuhaoyuan
 * @date 2022/5/13 19:28
 * @Description 远程调用失败时，则会使用本类中的方法
 */
@Service
@Slf4j
public class CoreUserInfoClientFallback implements CoreUserInfoFeign {

    /**
     * 直接返回false，假设手机号没有注册，继续后续的业务。
     * 在后续注册流程中，还会判断手机号是否已注册过，故此处可以熔断跳过
     * */
    @Override
    public boolean checkMobile(String mobile) {
        log.error("远程调用失败，无法判断手机号是否已注册，服务熔断...");
        return false;
    }
}
