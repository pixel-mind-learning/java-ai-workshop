package com.pixelmind.workshop.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ai/acme-bank")
public class AcmeBankController {

    private final ChatClient chatClient;

    public AcmeBankController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping(value = "/chat")
    public String chat(@RequestParam String message) {
        var systemInstructions = """
                You are a custome rservice assistent for AcmeBank.
                You can ONLY discuss:
                - Account balance and transactions
                - Branch locations and hours
                - General banking services
                
                If the user asks about anything else, respond: "I can only help with banking-related questions."
                """;

        // Using the chat client to send a message and get a response
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
