package cn.szut.dazuoye.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PracticeRequest(
        @NotBlank(message = "编程语言不能为空")
        String language,

        @NotBlank(message = "知识点不能为空")
        String topic,

        @Min(value = 1, message = "难度最小为 1")
        @Max(value = 5, message = "难度最大为 5")
        int difficulty,

        @Min(value = 1, message = "题量最小为 1")
        @Max(value = 5, message = "题量最大为 5")
        int count
) {
    public PracticeRequest {
        if (difficulty <= 0) {
            difficulty = 2;
        }
        if (count <= 0) {
            count = 3;
        }
    }
}
