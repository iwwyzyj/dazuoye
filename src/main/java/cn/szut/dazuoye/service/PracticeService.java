package cn.szut.dazuoye.service;

import cn.szut.dazuoye.client.ZhipuApiClient;
import cn.szut.dazuoye.dto.PracticeProblem;
import cn.szut.dazuoye.dto.PracticeRequest;
import cn.szut.dazuoye.prompt.PromptTemplates;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@Service
public class PracticeService {

    private final ZhipuApiClient zhipuApiClient;
    private final JsonMapper jsonMapper;

    public PracticeService(ZhipuApiClient zhipuApiClient, JsonMapper jsonMapper) {
        this.zhipuApiClient = zhipuApiClient;
        this.jsonMapper = jsonMapper;
    }

    public List<PracticeProblem> generate(PracticeRequest request) {
        var raw = zhipuApiClient.chat(
                PromptTemplates.PRACTICE_SYSTEM,
                PromptTemplates.practiceUser(
                        request.language(),
                        request.topic(),
                        request.difficulty(),
                        request.count()
                )
        );
        return parseProblems(raw);
    }

    private List<PracticeProblem> parseProblems(String raw) {
        var json = extractJsonArray(raw);
        try {
            return jsonMapper.readValue(json, new TypeReference<List<PracticeProblem>>() {});
        } catch (Exception ex) {
            throw new IllegalStateException("练习题 JSON 解析失败，请重试", ex);
        }
    }

    private String extractJsonArray(String raw) {
        var trimmed = raw.trim();
        if (trimmed.startsWith("```")) {
            var start = trimmed.indexOf('[');
            var end = trimmed.lastIndexOf(']');
            if (start >= 0 && end > start) {
                return trimmed.substring(start, end + 1);
            }
        }
        var start = trimmed.indexOf('[');
        var end = trimmed.lastIndexOf(']');
        if (start >= 0 && end > start) {
            return trimmed.substring(start, end + 1);
        }
        return trimmed;
    }
}
