package com.atguigu.srb.oss.server;

import java.io.InputStream;

/**
 * @author 吴浩远
 * @date 2022/5/13 9:10
 * @Description
 */
public interface FileService {
    /**
     * 文件上传至阿里云
     *
     * @param inputStream 文件流
     * @param module      文件类型
     * @param fileName    文件名
     * @return String 文件的url地址
     */
    String upload(InputStream inputStream, String module, String fileName);

    /**
     * 删除oss文件
     *
     * @param url 文件url地址
     */
    void removeFile(String url);
}
