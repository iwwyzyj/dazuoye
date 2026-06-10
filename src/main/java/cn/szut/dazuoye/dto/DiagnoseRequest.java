package cn.szut.dazuoye.dto;

import jakarta.validation.constraints.NotBlank;

public record DiagnoseRequest(
        @NotBlank(message = "编程语言不能为空")
        String language,

        @NotBlank(message = "代码不能为空")
        String code,

        @NotBlank(message = "错误信息不能为空")
        String errorMessage
) {}
