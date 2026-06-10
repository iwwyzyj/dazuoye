package cn.szut.dazuoye.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zhipu.api")
public record ZhipuProperties(
        String baseUrl,
        String key,
        String model,
        int maxTokens,
        int timeoutSeconds
) {
}
