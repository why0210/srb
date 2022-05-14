package com.atguigu.srb.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴浩远
 * @date 2022/5/12 11:11
 * @Description 统一返回类型
 */
@Data
public class R {

    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    private R() {
    }

    /**
     * 返回成功结果
     *
     * @return R
     */
    public static R ok() {
        R r = new R();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 返回失败结果
     *
     * @return R
     */
    public static R error() {
        R r = new R();
        r.setCode(ResponseEnum.ERROR.getCode());
        r.setMessage(ResponseEnum.ERROR.getMessage());
        return r;
    }

    /**
     * 返回特定结果
     *
     * @param responseEnum
     * @return R
     */
    public static R setResult(ResponseEnum responseEnum) {
        R r = new R();
        r.setCode(responseEnum.getCode());
        r.setMessage(responseEnum.getMessage());
        return r;
    }

    /**
     * 封装返回数据
     *
     * @param key
     * @param value
     * @return R
     */
    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 封装返回数据，若为Map集合，则直接赋值即可
     *
     * @param map
     * @return R
     */
    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    /**
     * 设置特定的消息
     *
     * @param message
     * @return R
     */
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 设置特定的响应码
     *
     * @param code
     * @return R
     */
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }
}
