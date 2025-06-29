package com.pixelmind.workshop.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping(value = "/ai/chat")
    public String chat() {
        // Using the chat client to send a message and get a response
        return chatClient.prompt()
                .user("Tell me an interesting fact about Java")
                .call()
                .content();
    }

    @GetMapping(value = "/chat/stream")
    public Flux<String> chatStream() {
        // Using the chat client to send a message and get a streamed response
        return chatClient.prompt()
                .user("I'm visiting Srilanka, can you give me 10 interesting places to visit?")
                .stream()
                .content();
    }

    @GetMapping(value = "/chat/joke")
    public ChatResponse joke() {
        // Using the chat client to send a message and get a response
        return chatClient.prompt()
                .user("Tell me a dad joke about dogs")
                .call()
                .chatResponse();
    }
}
