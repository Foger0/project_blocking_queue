import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class GPT3KeywordExtractor {
    private static final String API_KEY = "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";
    private static final String GPT3_API_URL = "https://api.openai.com/v1/engines/davinci/completions";

    public static void main(String[] args) {
        String userInput = "明天每隔一小时提醒我完成作业";
        try {
            String extractedKeywords = extractKeywords(userInput);
            System.out.println("关键词: " + extractedKeywords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extractKeywords(String userInput) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject body = new JSONObject();
        body.put("prompt", "提取时间: " + userInput);
        body.put("max_tokens", 100);
        body.put("n", 1);
        body.put("stop", (JSONArray)null);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body.toString());

        Request request = new Request.Builder()
                .url(GPT3_API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("请求失败: " + response);
            }

            JSONObject jsonResponse = new JSONObject(response.body().string());
            String extractedKeywords = jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text");
            return extractedKeywords.trim();
        }
    }
}