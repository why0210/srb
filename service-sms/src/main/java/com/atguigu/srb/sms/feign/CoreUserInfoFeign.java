package com.atguigu.srb.sms.feign;

import com.atguigu.srb.sms.feign.fallback.CoreUserInfoClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wuhaoyuan
 * @date 2022/5/13 18:48
 * @Description 远程调用服务，判断手机号是否已经注册，若远程调用失败，
 *              则服务熔断，而选择去调用本地类CoreUserInfoClientFallback中所实现的方法。
 */
@FeignClient(value = "service-core", fallback = CoreUserInfoClientFallback.class)
public interface CoreUserInfoFeign {
    /**
     * @Description 校验手机号是否已经被注册，请求路径必须填写完整
     * @param mobile
     * @return boolean
     */
    @GetMapping("/api/core/userInfo/checkMobile/{mobile}")
    boolean checkMobile(@PathVariable String mobile);
}
