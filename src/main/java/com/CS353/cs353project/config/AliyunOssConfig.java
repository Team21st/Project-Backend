package com.CS353.cs353project.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssConfig implements InitializingBean {

    /**
     * 阿里云 oss 站点
     */
    private String endpoint;

    /**
     * 阿里云 oss 公钥
     */
    private String accessKeyId;

    /**
     * 阿里云 oss 私钥
     */
    private String accessKeySecret;

    /**
     * 阿里云 oss 文件根目录
     */
    private String bucketName;

    /**
     * url有效期(S)
     */
    private Long policyExpire;

    /**
     * 上传文件大小(M)
     */
    private Long maxSize;

    // 方便直接获取
    public static String JAVA_END_POINT;
    public static String JAVA_ACCESS_KEY_ID;
    public static String JAVA_ACCESS_KEY_SECRET;
    public static String JAVA_BUCKET_NAME;
    public static Long JAVA_POLICY_EXPIRE;
    public static Long JAVA_MAX_SIZE;

    @Override
    public void afterPropertiesSet() {
        JAVA_END_POINT = endpoint;
        JAVA_ACCESS_KEY_ID = accessKeyId;
        JAVA_ACCESS_KEY_SECRET = accessKeySecret;
        JAVA_BUCKET_NAME = bucketName;
        JAVA_POLICY_EXPIRE = policyExpire;
        JAVA_MAX_SIZE = maxSize;
    }

}
