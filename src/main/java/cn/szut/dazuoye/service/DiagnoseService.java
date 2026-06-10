package cn.szut.dazuoye.service;

import cn.szut.dazuoye.client.ZhipuApiClient;
import cn.szut.dazuoye.dto.DiagnoseRequest;
import cn.szut.dazuoye.prompt.PromptTemplates;
import org.springframework.stereotype.Service;

@Service
public class DiagnoseService {

    private final ZhipuApiClient zhipuApiClient;

    public DiagnoseService(ZhipuApiClient zhipuApiClient) {
        this.zhipuApiClient = zhipuApiClient;
    }

    public String diagnose(DiagnoseRequest request) {
        return zhipuApiClient.chat(
                PromptTemplates.DIAGNOSE_SYSTEM,
                PromptTemplates.diagnoseUser(request.language(), request.code(), request.errorMessage())
        );
    }
}
