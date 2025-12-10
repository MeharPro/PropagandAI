package com.elssieAi.PropagandAi.Controllers;

import com.elssieAi.PropagandAi.Models.ChatBotResponse;
import com.elssieAi.PropagandAi.Services.ChatBotService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat/{ModelName}")
public class ChatBotController {

    private final ChatBotService chatBotService;

    public ChatBotController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    @GetMapping
    public ChatBotResponse getChatResponse(@PathVariable String ModelName, @RequestParam String prompt, @RequestParam String conversationId) {
        return chatBotService.getChatResponse(ModelName, prompt, conversationId);
    }
}