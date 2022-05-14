package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author wuhaoyuan
 * @since 2022-05-11
 */
public interface UserLoginRecordService extends IService<UserLoginRecord> {

    /**
     * 查询前50条用户日志
     *
     * @param userId 用户id
     * @return 日志列表
     */
    List<UserLoginRecord> listTop50(Long userId);
}
