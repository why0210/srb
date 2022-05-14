package com.atguigu.srb.sms.controller.api;

import com.atguigu.srb.common.exception.Assert;
import com.atguigu.srb.common.result.R;
import com.atguigu.srb.common.result.ResponseEnum;
import com.atguigu.srb.common.util.RandomUtils;
import com.atguigu.srb.common.util.RegexValidateUtils;
import com.atguigu.srb.sms.feign.CoreUserInfoFeign;
import com.atguigu.srb.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 吴浩远
 * @date 2022/5/12 21:52
 * @Description
 */
@RestController
@RequestMapping("/api/sms")
@Api(tags = "短信管理")
@Slf4j
public class ApiSmsController {
    @Autowired
    private SmsService smsService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 注意：此处应使用@Resource来注入，即按照名字来注入（这里是想注入接口）。
     * 因为这里有两个类型均为CoreUserInfoFeign的Bean，
     * 一个是CoreUserInfoFeign接口，一个是名字为CoreUserInfoClientFallback的实现类，
     * 它俩的类型相同，无法使用Autowired来注入
     * */
    @Resource
    private CoreUserInfoFeign coreUserInfoFeign;

    @ApiOperation("获取验证码")
    @GetMapping("/send/{mobile}")
    public R send(
            @ApiParam(value = "手机号", required = true)
            @PathVariable String mobile) {
        if(redisTemplate.opsForValue().get("srb:sms:code:"+mobile) != null){
            log.error("验证码频繁请求，code:"+redisTemplate.opsForValue().get("srb:sms:code:"+mobile));
            return R.setResult(ResponseEnum.ALIYUN_SMS_LIMIT_CONTROL_ERROR);
        }
        // 校验手机号是否为空
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        // 校验手机号是否合法
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        // 判断手机号是否已经被注册（远程调用）
        boolean result = coreUserInfoFeign.checkMobile(mobile);
        Assert.isTrue(!result, ResponseEnum.MOBILE_EXIST_ERROR);

        // 随机出一个四位数的验证码
        Map<String, Object> map = new HashMap<>();
        String code = RandomUtils.getFourBitRandom();
        map.put("code", code);
        // 调用容联云短信服务发送短信
        smsService.send(mobile, map);

        // 将验证码存入Redis，过期时间为5分钟
        redisTemplate.opsForValue().set("srb:sms:code:" + mobile, code, 5, TimeUnit.MINUTES);
        return R.ok().message("短信发送成功");
    }
}
