package com.atguigu.srb.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanhaowu
 * @Description 用于查询操作的VO
 */
@Data
@ApiModel(description = "会员搜索对象")
public class UserInfoQuery {

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "用户类型 1：出借人 2：借款人")
    private String userType;
}
