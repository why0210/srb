package com.atguigu.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴浩远
 * @date 2022/5/13 13:41
 * @Description 此VO用于用户注册
 */

@Data
@ApiModel(description = "注册对象")
public class RegisterVO {

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "密码")
    private String password;
}
