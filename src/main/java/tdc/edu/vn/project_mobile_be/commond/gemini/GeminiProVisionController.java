package tdc.edu.vn.project_mobile_be.commond.gemini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tdc.edu.vn.project_mobile_be.services.impl.GeminiServiceImpl;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/gemini")
public class GeminiProVisionController {

    @Autowired
    private GeminiServiceImpl geminiService;

    @PostMapping("/chat")
    public String chat(@RequestParam String question) throws IOException {
        return geminiService.chatDiscussion(question);
    }

    @PostMapping("/generate")
    public CompletableFuture<String> generateContent(
            @RequestParam("prompt") String prompt,
            @RequestParam("productId") UUID productId) throws IOException {
        return geminiService.generateContent(prompt, productId);
    }
}