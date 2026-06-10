package cn.szut.dazuoye.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(ZhipuProperties.class)
public class AppConfig {

    @Bean
    RestClient zhipuRestClient(ZhipuProperties properties) {
        var httpFactory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
        httpFactory.setConnectTimeout(Duration.ofSeconds(15));
        httpFactory.setReadTimeout(Duration.ofSeconds(properties.timeoutSeconds()));

        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(httpFactory)
                .build();
    }
}
