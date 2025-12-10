package com.elssieAi.PropagandAi.Services;

import com.elssieAi.PropagandAi.Models.ChatBotResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.memory.ChatMemory;

@Service
public class ChatBotService {

    private final ChatClient chatClient;
    private final MessageChatMemoryAdvisor chatMemoryAdvisor;
    private final RetrievalAugmentationAdvisor retrievalAugmentationAdvisor;

    public ChatBotService(ChatClient.Builder chatClientBuilder, @Qualifier("applicationTaskExecutor") TaskExecutor taskExecutor, ChatMemory chatMemory, RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
        this.chatClient = chatClientBuilder.build();
        this.chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        this.retrievalAugmentationAdvisor = retrievalAugmentationAdvisor;
    }

    public ChatBotResponse getChatResponse(String modelName, String prompt, String conversationId) {
        return new ChatBotResponse(chatClient.prompt()
                .advisors(chatMemoryAdvisor, retrievalAugmentationAdvisor)
                .advisors(advisors -> advisors.param("conversationId", conversationId))
                .user(prompt)
                .options(ChatOptions.builder().model(modelName).build())
                .call()
                .content());
    }
}