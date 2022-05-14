package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.core.pojo.entity.UserLoginRecord;
import com.atguigu.srb.core.mapper.UserLoginRecordMapper;
import com.atguigu.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author wuhaoyuan
 * @since 2022-05-11
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

    @Override
    public List<UserLoginRecord> listTop50(Long userId) {
        List<UserLoginRecord> userLoginRecords = this.baseMapper.selectList(new QueryWrapper<UserLoginRecord>()
                .eq("user_id", userId)
                .orderByDesc("id")
                .last("limit 50"));
        return userLoginRecords;
    }
}
