package com.CS353.cs353project.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "white")
@Data
public class HCRMSProperties {
    //无需验证的路由
    private List<String> loginInterceptorExcludePath;
}