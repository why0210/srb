package com.atguigu.srb.core.controller.admin;

import com.atguigu.srb.common.result.R;
import com.atguigu.srb.core.pojo.entity.UserLoginRecord;
import com.atguigu.srb.core.service.UserLoginRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuhaoyuan
 * @date 2022/5/13 18:07
 * @Description
 */
@Api(tags = "会员登录日志接口")
@RestController
@RequestMapping("/admin/core/userLoginRecord")
@Slf4j
public class AdminUserLoginRecordController {
    @Autowired
    private UserLoginRecordService userLoginRecordService;

    @ApiOperation("获取会员登录日志列表")
    @GetMapping("/listTop50/{userId}")
    public R listTop50(
            @ApiParam(value = "用户id", required = true)
            @PathVariable("userId") Long userId) {
        List<UserLoginRecord> list = userLoginRecordService.listTop50(userId);
        return R.ok().data("list", list);
    }
}
