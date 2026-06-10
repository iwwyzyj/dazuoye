package cn.szut.dazuoye.dto;

public record PracticeProblem(
        String title,
        String description,
        String hint,
        String sampleInput,
        String sampleOutput,
        String referenceAnswer
) {}
