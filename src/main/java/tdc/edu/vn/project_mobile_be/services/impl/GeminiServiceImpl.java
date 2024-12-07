package tdc.edu.vn.project_mobile_be.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.responses.product.ProductResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.service.GeminiService;
import tdc.edu.vn.project_mobile_be.interfaces.service.ProductService;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class GeminiServiceImpl implements GeminiService {
    @Autowired
    private GenerativeModel generativeModel;
    @Autowired
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String chatDiscussion(String prompt) throws IOException {
        String result = "";
        try {
            ChatSession chatSession = new ChatSession(generativeModel);
            GenerateContentResponse response = chatSession.sendMessage(prompt);
            result = ResponseHandler.getText(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public CompletableFuture<String> generateContent(String prompt, UUID productId) throws IOException {


        String productJson;
        try {
            ProductResponseDTO productDTO = productService.getProductById(productId);
            productJson = objectMapper.writeValueAsString(productDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture("Error processing product data.");
        }

        String fullPrompt = String.format("Yêu cầu: %s\n\n---\n" + "Dữ liệu sản phẩm (JSON):\n%s\n" + "---\n\n" + "Hãy thực hiện yêu cầu dựa trên dữ liệu sản phẩm trên.", prompt, productJson);

        GenerativeModel model = generativeModel;
        GenerateContentResponse responseFuture = model.generateContent(fullPrompt);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ResponseHandler.getText(responseFuture);
            } catch (Exception e) {
                e.printStackTrace();
                return "Error generating content.";
            }
        });
    }
}