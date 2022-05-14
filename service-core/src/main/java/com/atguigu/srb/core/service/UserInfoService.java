package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.UserInfo;
import com.atguigu.srb.core.pojo.query.UserInfoQuery;
import com.atguigu.srb.core.pojo.vo.LoginVO;
import com.atguigu.srb.core.pojo.vo.RegisterVO;
import com.atguigu.srb.core.pojo.vo.UserIndexVO;
import com.atguigu.srb.core.pojo.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author wuhaoyuan
 * @since 2022-05-11
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 进行用户注册
     * @param registerVO 用户注册信息
     * */
    void register(RegisterVO registerVO);

    /**
     * 实现用户登录
     *
     * @param loginVO 用户输入
     * @param ip      登录ip
     * @return 用户信息对象
     */
    UserInfoVO login(LoginVO loginVO, String ip);

    /**
     * 分页查询用户信息
     * */
    IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery);

    /**
     * 实现用户锁定
     *
     * @param id     待锁定的用户id
     * @param status
     */
    void lock(Long id, Integer status);

    /**
     * 校验手机号是否已经被注册
     *
     * @param mobile 手机号
     * @return 返回是否被注册，true：已经被注册；false：未被注册
     */
    boolean checkMobile(String mobile);

    UserIndexVO getIndexUserInfo(Long userId);

    String getMobileByBindCode(String bindCode);
}
