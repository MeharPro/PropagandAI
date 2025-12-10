package com.elssieAi.PropagandAi.Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ShowModelRequest(String model, String system, Boolean verbose, Map<String, Object> options) {
        public ShowModelRequest(String model, Boolean verbose) {
            this(model, (String) null, verbose, (Map) null);
        }

        public ShowModelRequest(@JsonProperty("model") String model, @JsonProperty("system") String system, @JsonProperty("verbose") Boolean verbose, @JsonProperty("options") Map<String, Object> options) {
            this.model = model;
            this.system = system;
            this.verbose = verbose;
            this.options = options;
        }
    }
