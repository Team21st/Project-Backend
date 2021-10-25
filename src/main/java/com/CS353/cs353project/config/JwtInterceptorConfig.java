package com.CS353.cs353project.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lehr
 */
@Configuration
@EnableConfigurationProperties(SHBMProperties.class)
public class JwtInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private final SHBMProperties SHBMProperties;

    public JwtInterceptorConfig(SHBMProperties SHBMProperties) {
        this.SHBMProperties = SHBMProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //默认拦截所有路径
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(SHBMProperties.getLoginInterceptorExcludePath().toArray(new String[]{}));//白名单URL;
    }

    @Bean
    public JwtAuthenticationInterceptor authenticationInterceptor() {
        return new JwtAuthenticationInterceptor();
    }
}