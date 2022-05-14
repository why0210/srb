package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.UserBind;
import com.atguigu.srb.core.pojo.vo.UserBindVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author wuhaoyuan
 * @since 2022-05-11
 */
public interface UserBindService extends IService<UserBind> {

    /**
     * 通过前台用户输入的信息动态组装一个提交表单，该表单能够实现自动提交（使用script脚本自动提交表单）
     *
     * @param userBindVO 用户信息
     * @param userId     用户Id
     * @return 动态表单字符串
     */
    String commitBindUser(UserBindVO userBindVO, Long userId);

    void notify(Map<String, Object> paramMap);

    String getBindCodeByUserId(Long userId);
}
