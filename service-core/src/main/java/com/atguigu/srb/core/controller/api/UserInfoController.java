package com.atguigu.srb.core.controller.api;


import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.common.exception.Assert;
import com.atguigu.srb.common.result.R;
import com.atguigu.srb.common.result.ResponseEnum;
import com.atguigu.srb.common.util.RegexValidateUtils;
import com.atguigu.srb.core.pojo.vo.LoginVO;
import com.atguigu.srb.core.pojo.vo.RegisterVO;
import com.atguigu.srb.core.pojo.vo.UserInfoVO;
import com.atguigu.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author wuhaoyuan
 * @since 2022-05-11
 */
@Api(tags = "会员接口")
@RestController
@RequestMapping("/api/core/userInfo")
@Slf4j
public class UserInfoController {

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("会员注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVO registerVO) {
        String mobile = registerVO.getMobile();
        String password = registerVO.getPassword();
        String code = registerVO.getCode();

        // 校验用户输入是否合法：手机号为空、密码为空、验证码为空、手机号格式不正确
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);
        Assert.notEmpty(code, ResponseEnum.CODE_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        // 校验验证码是否正确
        String codeGen = (String) redisTemplate.opsForValue().get("srb:sms:code:" + mobile);
        log.info("判断验证码是否正确...");
        Assert.equals(code, codeGen, ResponseEnum.CODE_ERROR);

        // 注册
        userInfoService.register(registerVO);
        return R.ok().message("注册成功");
    }

    @ApiOperation("会员登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();

        // 校验用户输入
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        //从HttpServletRequest中获取ip
        String ip = request.getRemoteAddr();
        UserInfoVO userInfoVO = userInfoService.login(loginVO, ip);
        return R.ok().data("userInfo", userInfoVO);
    }

    /**
     *
     * */
    @ApiOperation("校验令牌")
    @GetMapping("/checkToken")
    public R checkToken(HttpServletRequest request) {
        //从前端请求中获取token
        String token = request.getHeader("token");
        // 校验token
        boolean result = JwtUtils.checkToken(token);
        if (result) {
            log.info("token校验成功");
            return R.ok();
        } else {
            return R.setResult(ResponseEnum.LOGIN_AUTH_ERROR);
        }
    }

    @ApiOperation("校验手机号是否已经被注册")
    @GetMapping("/checkMobile/{mobile}")
    public boolean checkMobile(@PathVariable String mobile) {
        return userInfoService.checkMobile(mobile);
    }
}

