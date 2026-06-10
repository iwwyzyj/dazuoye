package cn.szut.dazuoye.client;

import java.util.List;

public record ChatCompletionResponse(
        List<Choice> choices
) {
    public record Choice(Message message) {}

    public record Message(String role, String content) {}

    public String content() {
        if (choices == null || choices.isEmpty()) {
            return "";
        }
        var message = choices.get(0).message();
        return message != null && message.content() != null ? message.content() : "";
    }
}
