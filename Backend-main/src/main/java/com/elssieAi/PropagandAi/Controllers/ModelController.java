package com.elssieAi.PropagandAi.Controllers;

import com.elssieAi.PropagandAi.Services.ModelService;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/models")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public OllamaApi.ListModelResponse getAllModels() {
        return modelService.getAllModels();
    }

    @GetMapping("{modelName}")
    public OllamaApi.ShowModelResponse getModelDetails(@PathVariable String modelName, @RequestParam(required = false) boolean verbose) {
        return modelService.getModelDetails(modelName, verbose);
    }
}