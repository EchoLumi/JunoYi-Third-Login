package com.junoyi.framework.file.config;

import com.junoyi.framework.file.properties.FileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * 文件存储自动配置
 *
 * @author Fan
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(FileStorageProperties.class)
@ComponentScan("com.junoyi.framework.file")
public class FileStorageConfiguration {

    public FileStorageConfiguration(FileStorageProperties properties) {
        log.info("[{}] File management module initialization completed.","File Manager");
        log.info("Current storage type：{}",properties.getStorageType().getName());
    }
}
