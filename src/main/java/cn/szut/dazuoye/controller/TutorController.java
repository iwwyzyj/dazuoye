package cn.szut.dazuoye.controller;

import cn.szut.dazuoye.dto.*;
import cn.szut.dazuoye.service.DiagnoseService;
import cn.szut.dazuoye.service.ExplainService;
import cn.szut.dazuoye.service.PracticeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TutorController {

    private final ExplainService explainService;
    private final DiagnoseService diagnoseService;
    private final PracticeService practiceService;

    public TutorController(
            ExplainService explainService,
            DiagnoseService diagnoseService,
            PracticeService practiceService
    ) {
        this.explainService = explainService;
        this.diagnoseService = diagnoseService;
        this.practiceService = practiceService;
    }

    @GetMapping("/health")
    public ApiError health() {
        return new ApiError("ok");
    }

    @PostMapping("/explain")
    public ExplainResponse explain(@Valid @RequestBody ExplainRequest request) {
        return new ExplainResponse(explainService.explain(request));
    }

    @PostMapping("/diagnose")
    public DiagnoseResponse diagnose(@Valid @RequestBody DiagnoseRequest request) {
        return new DiagnoseResponse(diagnoseService.diagnose(request));
    }

    @PostMapping("/practice/generate")
    public PracticeResponse generatePractice(@Valid @RequestBody PracticeRequest request) {
        return new PracticeResponse(practiceService.generate(request));
    }
}
