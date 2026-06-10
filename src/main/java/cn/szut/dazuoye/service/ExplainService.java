package cn.szut.dazuoye.service;

import cn.szut.dazuoye.client.ZhipuApiClient;
import cn.szut.dazuoye.dto.ExplainRequest;
import cn.szut.dazuoye.prompt.PromptTemplates;
import org.springframework.stereotype.Service;

@Service
public class ExplainService {

    private final ZhipuApiClient zhipuApiClient;

    public ExplainService(ZhipuApiClient zhipuApiClient) {
        this.zhipuApiClient = zhipuApiClient;
    }

    public String explain(ExplainRequest request) {
        return zhipuApiClient.chat(
                PromptTemplates.EXPLAIN_SYSTEM,
                PromptTemplates.explainUser(request.language(), request.code(), request.level())
        );
    }
}
