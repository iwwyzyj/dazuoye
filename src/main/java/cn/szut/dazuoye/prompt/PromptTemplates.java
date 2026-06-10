package cn.szut.dazuoye.prompt;

public final class PromptTemplates {

    private PromptTemplates() {}

    public static final String EXPLAIN_SYSTEM = """
            你是面向编程初学者的智能辅导老师。请始终使用中文回答，结构清晰，语言通俗易懂。
            解释代码时避免堆砌术语，必要时用生活化类比帮助理解。
            """;

    public static String explainUser(String language, String code, String level) {
        return """
                请解释以下 %s 代码（解释深度：%s）：

                请按以下结构回答（使用 Markdown 标题）：
                ## 整体功能
                用一句话概括代码做什么。

                ## 逐段说明
                按逻辑块逐段解释，标注关键行。

                ## 核心概念
                列出涉及的编程概念并简要说明。

                ## 小例子
                给出一个更简单的类似例子帮助理解。

                代码：
                ```%s
                %s
                ```
                """.formatted(language, levelLabel(level), language.toLowerCase(), code);
    }

    public static final String DIAGNOSE_SYSTEM = """
            你是编程调试助手，专门帮助初学者理解和修复代码错误。
            请用中文回答，语气耐心友好，先讲原因再给修正方案。
            """;

    public static String diagnoseUser(String language, String code, String errorMessage) {
        return """
                请分析以下 %s 代码的错误并给出修正建议：

                【代码】
                ```%s
                %s
                ```

                【错误信息】
                %s

                请按以下结构回答（使用 Markdown 标题）：
                ## 错误原因
                用通俗语言解释为什么会出错。

                ## 出错位置
                指出具体行或代码片段。

                ## 修正方案
                给出修正后的完整代码（代码块）。

                ## 避坑建议
                列出 1-2 条避免类似错误的建议。
                """.formatted(language, language.toLowerCase(), code, errorMessage);
    }

    public static final String PRACTICE_SYSTEM = """
            你是编程练习题出题专家，面向初学者设计循序渐进、贴近实际的练习题。
            你必须只输出合法 JSON 数组，不要包含 markdown 代码块或其他文字。
            """;

    public static String practiceUser(String language, String topic, int difficulty, int count) {
        return """
                请生成 %d 道 %s 编程练习题。
                知识点：%s
                难度：%d/5

                返回 JSON 数组，每个元素格式如下：
                {
                  "title": "题目标题",
                  "description": "题目描述（含输入输出要求）",
                  "hint": "提示（不要直接给完整答案）",
                  "sampleInput": "示例输入",
                  "sampleOutput": "示例输出",
                  "referenceAnswer": "参考答案代码"
                }

                只输出 JSON 数组，不要其他内容。
                """.formatted(count, language, topic, difficulty);
    }

    private static String levelLabel(String level) {
        return switch (level == null ? "beginner" : level.toLowerCase()) {
            case "advanced" -> "进阶";
            case "intermediate" -> "中级";
            default -> "入门";
        };
    }
}
