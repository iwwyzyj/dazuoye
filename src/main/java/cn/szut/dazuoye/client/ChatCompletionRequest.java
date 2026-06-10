package cn.szut.dazuoye.client;

import java.util.List;

public record ChatCompletionRequest(
        String model,
        List<Message> messages,
        int max_tokens,
        double temperature,
        boolean stream
) {
    public record Message(String role, String content) {}
}
