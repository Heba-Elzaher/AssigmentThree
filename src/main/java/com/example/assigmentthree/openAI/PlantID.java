package com.example.assigmentthree.openAI;

import okhttp3.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Configuration
public class PlantID {

    @Value("${plant.id.api.key}")
    private String apiKey;
    @Value("${plant.id.url}")
    private String apiUrl;

    public String treeIdentifier(byte[] imageBytes) {
        OkHttpClient httpClient = new OkHttpClient();

        Path tempFilePath = createTempFile(imageBytes);
        if (tempFilePath == null) {
            return "Error creating temporary file";
        }

        RequestBody requestBody = createRequestBody(tempFilePath);
        Request request = buildRequest(requestBody);

        try (Response response = httpClient.newCall(request).execute()) {
            String responseString = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseString).getAsJsonObject();
            return parseSpeciesName(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error identifying tree";
        } finally {
            deleteTempFile(tempFilePath);
        }
    }

    private Path createTempFile(byte[] imageBytes) {
        try {
            Path tempFilePath = Files.createTempFile("treeImage_" + UUID.randomUUID(), ".jpg");
            Files.write(tempFilePath, imageBytes);
            return tempFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private RequestBody createRequestBody(Path filePath) {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("organs", "leaf")
                .addFormDataPart("images", filePath.getFileName().toString(),
                        RequestBody.create(filePath.toFile(), MediaType.parse("image/jpeg")))
                .build();
    }

    private Request buildRequest(RequestBody requestBody) {
        return new Request.Builder()
                .url(apiUrl)
                .addHeader("Api-Key", apiKey)
                .post(requestBody)
                .build();
    }

    private String parseSpeciesName(JsonObject jsonResponse) {
        JsonArray suggestions = jsonResponse.getAsJsonArray("suggestions");
        if (suggestions.size() > 0) {
            return suggestions.get(0).getAsJsonObject().get("plant_name").getAsString();
        } else {
            return "Unknown";
        }
    }

    private void deleteTempFile(Path tempFilePath) {
        try {
            Files.deleteIfExists(tempFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
