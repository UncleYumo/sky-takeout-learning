package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author uncle_yumo
 * @fileName CommonController
 * @createDate 2025/2/20 February
 * @school 无锡学院
 * @studentID 22344131
 * @description 通用控制器，用于上传文件等
 */

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用控制器接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("上传文件：{}", file.getOriginalFilename());
        String fileUrl = null;
        try {
            String originalFilename = file.getOriginalFilename();
            // 截取原始文件名的后缀名
            if (originalFilename == null) {
                log.error("上传文件失败：文件名不能为空");
                return Result.error("上传文件失败：文件名不能为空");
            }
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + suffix;
            fileUrl = aliOssUtil.upload(file.getBytes(), newFileName);
        } catch (IOException e) {
            log.error("上传文件失败：{}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Result.success(fileUrl);
    }
}