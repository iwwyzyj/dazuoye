package cn.szut.dazuoye.client;

import cn.szut.dazuoye.config.ZhipuProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Component
public class ZhipuApiClient {

    private final RestClient restClient;
    private final ZhipuProperties properties;

    public ZhipuApiClient(RestClient zhipuRestClient, ZhipuProperties properties) {
        this.restClient = zhipuRestClient;
        this.properties = properties;
    }

    public String chat(String systemPrompt, String userPrompt) {
        if (properties.key() == null || properties.key().isBlank()) {
            throw new IllegalStateException("未配置 ZHIPUAI_API_KEY 环境变量");
        }

        var request = new ChatCompletionRequest(
                properties.model(),
                List.of(
                        new ChatCompletionRequest.Message("system", systemPrompt),
                        new ChatCompletionRequest.Message("user", userPrompt)
                ),
                properties.maxTokens(),
                0.7,
                false
        );

        try {
            var response = restClient.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + properties.key())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(ChatCompletionResponse.class);

            if (response == null || response.content().isBlank()) {
                throw new IllegalStateException("智谱 API 返回空内容");
            }
            return response.content();
        } catch (RestClientResponseException ex) {
            throw new IllegalStateException("智谱 API 调用失败: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString(), ex);
        }
    }
}
