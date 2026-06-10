package cn.szut.dazuoye.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ExplainRequest(
        @NotBlank(message = "编程语言不能为空")
        String language,

        @NotBlank(message = "代码不能为空")
        String code,

        @Pattern(regexp = "beginner|intermediate|advanced", message = "level 必须为 beginner、intermediate 或 advanced")
        String level
) {
    public ExplainRequest {
        if (level == null || level.isBlank()) {
            level = "beginner";
        }
    }
}
