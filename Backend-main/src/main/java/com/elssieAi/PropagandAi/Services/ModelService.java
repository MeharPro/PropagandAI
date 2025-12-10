package com.elssieAi.PropagandAi.Services;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ModelService {

    private final OllamaApi ollamaApi;

    @Value("${spring.ai.ollama.embedding.options.model}")
    private String embeddingModelName;

    public ModelService(OllamaApi ollamaApi) {
        this.ollamaApi = ollamaApi;
    }

    public OllamaApi.ListModelResponse getAllModels() {
        OllamaApi.ListModelResponse response = ollamaApi.listModels();
        List<OllamaApi.Model> filteredModels = response.models().stream()
                .filter(model -> !model.name().equals(embeddingModelName))
                .toList();
        return new OllamaApi.ListModelResponse(filteredModels);
    }

    public OllamaApi.ShowModelResponse getModelDetails(String modelName, boolean verbose) {
        return ollamaApi.showModel(new OllamaApi.ShowModelRequest(modelName, null, verbose, null));
    }
}