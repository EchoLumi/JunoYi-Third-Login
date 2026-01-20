package com.junoyi.framework.file.config;

import com.junoyi.framework.file.factory.FileStorageFactory;
import com.junoyi.framework.file.helper.FileHelper;
import com.junoyi.framework.file.properties.FileStorageProperties;
import com.junoyi.framework.file.storage.AliyunOssFileStorage;
import com.junoyi.framework.file.storage.FileStorage;
import com.junoyi.framework.file.storage.LocalFileStorage;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 文件存储自动配置
 *
 * @author Fan
 */
@AutoConfiguration
@EnableConfigurationProperties(FileStorageProperties.class)
public class FileStorageConfiguration {

    private static final JunoYiLog log = JunoYiLogFactory.getLogger(FileStorageConfiguration.class);

    /**
     * 文件存储实例
     * 根据配置动态创建对应的存储实现
     */
    @Bean
    @ConditionalOnMissingBean
    public FileStorage fileStorage(FileStorageProperties properties) {
        switch (properties.getStorageType()) {
            case LOCAL:
                log.info("File Manager", "Local file storage initialized, base path: " + 
                        properties.getLocal().getBasePath());
                return new LocalFileStorage(properties);
                
            case ALIYUN_OSS:
                log.info("File Manager", "Aliyun OSS file storage initialized, bucket: " +
                        properties.getAliyunOss().getBucketName());
                return new AliyunOssFileStorage(properties);
                
            case MINIO:
                throw new UnsupportedOperationException("MinIO存储暂未实现");
                
            case QINIU:
                throw new UnsupportedOperationException("七牛云存储暂未实现");
                
            case TENCENT_COS:
                throw new UnsupportedOperationException("腾讯云COS存储暂未实现");
                
            default:
                throw new IllegalArgumentException("不支持的存储类型: " + properties.getStorageType());
        }
    }

    /**
     * 文件存储工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public FileStorageFactory fileStorageFactory(FileStorageProperties properties, FileStorage fileStorage) {
        log.info("File Manager","File storage factory initialized, current type: " +
                properties.getStorageType().getName());
        return new FileStorageFactory(properties, fileStorage);
    }

    /**
     * 文件助手
     */
    @Bean
    @ConditionalOnMissingBean
    public FileHelper fileHelper(FileStorageFactory fileStorageFactory) {
        log.info("File Manager","File helper initialized");
        return new FileHelper(fileStorageFactory);
    }
}
