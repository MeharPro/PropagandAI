package com.elssieAi.PropagandAi.ControllerTests;

import com.elssieAi.PropagandAi.Services.ModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ai.ollama.api.OllamaApi;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ModelControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ModelService modelService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllModelsReturns200OK() throws Exception {
        OllamaApi.ListModelResponse response = new OllamaApi.ListModelResponse(List.of());

        when(modelService.getAllModels()).thenReturn(response);

        mockMvc.perform(get("/models"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetModelDetailsReturns200OK() throws Exception {
        String modelName = "testModel";
        boolean verbose = true;

        when(modelService.getModelDetails(modelName, verbose)).thenReturn(null);

        mockMvc.perform(get("/models/{modelName}", modelName)
                        .param("verbose", String.valueOf(verbose)))
                .andExpect(status().isOk());
    }
}