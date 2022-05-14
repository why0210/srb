package com.atguigu.srb.oss.controller.api;

import com.atguigu.srb.common.exception.BusinessException;
import com.atguigu.srb.common.result.R;
import com.atguigu.srb.common.result.ResponseEnum;
import com.atguigu.srb.oss.server.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 吴浩远
 * @date 2022/5/13 9:06
 * @Description
 */
@Api(tags = "阿里云文件管理")
@RestController
@RequestMapping("/api/oss/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * @param file   文件实体
     * @param module 存储在oss的哪一个模块（哪一个文件夹中）
     */
    @ApiOperation("文件上传阿里云OSS")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(value = "模块", required = true)
            @RequestParam("module") String module) {
        try {
            // 获取文件流
            InputStream inputStream = file.getInputStream();
            // 获取文件扩展名
            String originalFilename = file.getOriginalFilename();
            String url = fileService.upload(inputStream, module, originalFilename);
            return R.ok().message("文件上传成功").data("url", url);
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    /**
     * @param url 要删除的文件的url
     */
    @ApiOperation("删除阿里云OSS文件")
    @DeleteMapping("/remove")
    public R remove(
            @ApiParam(value = "要删除的文件", required = true)
            @RequestParam("url") String url) {
        fileService.removeFile(url);
        return R.ok().message("文件删除成功");
    }
}
