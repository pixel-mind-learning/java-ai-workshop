package com.pixelmind.workshop.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/ai/article")
public class ArticalController {

    private final ChatClient chatClient;

    public ArticalController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping(value = "/posts/new")
    public String createNewPost(@RequestParam(value = "topic", defaultValue = "JDK Virtual Threads") String topic) {
        // A system message in LLMs is a special type of input that provides high-level instructions, context, or behavioral
        // guidelines to the model before processing user queries. Think of it as the "behind-the-scenes"
        // instructions that shape how the model should respond to user inputs.
        // Use it as a guide or a restriction to the model's behavior.

        var systemInstructions = """
                Blog Post Generator Guidelines:
                
                1. Length & Purpose: Generate 500-word blog posts that inform and engage general audiences.
                
                2. Structure:
                - Introduction: Hook readers and establish the topic's relevance
                - Body: Develop 3 main points with supporting evidence and examples
                - Conclusion: Summarize key takeaways and include a call-to-action
                
                3. Content Requirements:
                - Include real-world applications or case studies
                - Incorporate relevant statistics or data points when appropriate
                - Explain benefits/implications clearly for non-experts
                
                4. Style & Tone:
                - Write in an informative yet conversational voice 
                - Use accessible language while maintaining authority
                - Break up text with subheadings and short paragraphs
                
                5. Response Format: Deliver complete, ready-too-publish posts with a suggested title.
                """;
        return chatClient.prompt()
                .user(u -> {
                    u.text("Write a blog post about {topic}");
                    u.param("topic", topic);
                })
                .system(systemInstructions)
                .call()
                .content();
    }
}
