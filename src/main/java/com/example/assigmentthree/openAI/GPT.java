package com.example.assigmentthree.openAI;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

@Configuration
public class GPT {
    @Value("${open.ai.api.key}")
    private String openAiApiKey;

    @Value("${ai.id.url}")
    private String openAiUrl;

    private final OkHttpClient client = new OkHttpClient();

    public String getAiAnalysis(String treeSpecies) {
        String prompt = createPrompt(treeSpecies);
        JsonObject requestBody = createRequestBody(prompt);

        try (Response response = executeRequest(requestBody)) {
            return parseResponse(response);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get tree information", e);
        }
    }

    private String createPrompt(String treeSpecies) {
        return String.format(
                "The following is a tree that I have planted:\n" +
                        "Tree Species: %s\n" +
                        "Please provide detailed information about the tree's current rarity or endangerment level and how to take care of this tree as bullet points, including:\n" +
                        "1. The rarity or endangerment level of this tree species.\n" +
                        "2. How to take care of this tree, including its needs and the best tips and tricks to keep it healthy and ensure its survival.\n" +
                        "3. Based on the rarity of this tree, categorize the tree's rarity as very rare, rare, common, or very common.",
                treeSpecies
        );
    }

    private JsonObject createRequestBody(String prompt) {
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);

        JsonArray messagesArray = new JsonArray();
        messagesArray.add((JsonElement) message);

        JsonObject json = new JsonObject();
        json.add("messages", messagesArray);
        json.addProperty("model", "gpt-3.5-turbo");
        json.addProperty("max_tokens", 500);

        return json;
    }

    private Response executeRequest(JsonObject json) throws IOException {
        RequestBody requestBody = RequestBody.create(
                json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(openAiUrl)
                .addHeader("Authorization", "Bearer " + openAiApiKey)
                .post(requestBody)
                .build();

        return client.newCall(request).execute();
    }

    private String parseResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        String jsonResponse = response.body().string();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        if (jsonObject.has("choices") && jsonObject.get("choices").isJsonArray()) {
            return jsonObject.get("choices").getAsJsonArray()
                    .get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString().trim();
        } else {
            System.err.println("Error: 'choices' element not found in the response.");
            return "Error: Unable to retrieve tree information. 'choices' element not found.";
        }
    }
}
