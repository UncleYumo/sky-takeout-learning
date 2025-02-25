package com.sky.config;

/**
 * @author uncle_yumo
 * @fileName OssConfiguration
 * @createDate 2025/2/20 February
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // 声明为Spring配置类
@Slf4j  // 日志打印
public class OssConfiguration {

    /**
     * 初始化阿里云OSS工具类
     * @return AliOssUtil
     */
    @Bean  // 声明为Bean，当项目启动时，该Bean会被自动创建，并注入到其他Bean中
    @ConditionalOnMissingBean()  // 当容器中不存在AliOssUtil时，才会创建该Bean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建阿里云OSS工具类对象： {}", aliOssProperties);
        return new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName()
        );
    }
}
