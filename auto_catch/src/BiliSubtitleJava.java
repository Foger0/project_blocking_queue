import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BiliSubtitleJava {

    public static void main(String[] args) throws Exception {
        String url = "https://www.bilibili.com/video/BV194411y7sA?p=22";
        String subtitle = getSubtitleFromUrl(url);
        System.out.println(subtitle);
        saveSubtitleToFile(subtitle, "subtitle.txt");
    }
    public static void saveSubtitleToFile(String subtitle, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(subtitle);
        } catch (Exception e) {
            System.err.println("Error writing subtitle to file: " + e.getMessage());
        }
    }
    public static List<Integer> biliPlayerList(String bvid) throws Exception {
        String urlString = "https://api.bilibili.com/x/player/pagelist?bvid=" + bvid;
        JSONObject json = getJsonFromUrl(urlString);
        JSONArray data = json.getJSONArray("data");
        List<Integer> cidList = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            cidList.add(data.getJSONObject(i).getInt("cid"));
        }
        return cidList;
    }

    public static List<String> biliSubtitleList(String bvid, int cid) throws Exception {
        String urlString = String.format("https://api.bilibili.com/x/player/v2?bvid=%s&cid=%d", bvid, cid);
        JSONObject json = getJsonFromUrl(urlString);
        JSONArray subtitles = json.getJSONObject("data").getJSONObject("subtitle").getJSONArray("subtitles");
        List<String> subtitleUrls = new ArrayList<>();
        for (int i = 0; i < subtitles.length(); i++){
            subtitleUrls.add("https:" + subtitles.getJSONObject(i).getString("subtitle_url"));
        }
        return subtitleUrls;
    }

    public static String biliSubtitle(String bvid, int cid) throws Exception {
        List<String> subtitles = biliSubtitleList(bvid, cid);
        if (!subtitles.isEmpty()) {
            JSONObject json = getJsonFromUrl(subtitles.get(0));
            JSONArray jsonArray = json.getJSONArray("body"); // 修改这一行
            StringBuilder subtitleText = new StringBuilder();
            for (int i = 0; i < jsonArray.length(); i++) {
                subtitleText.append(jsonArray.getJSONObject(i).getString("content"));
                subtitleText.append("\n");
            }
            return subtitleText.toString();
        }
        return "";
    }

    public static JSONObject getJsonFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        return new JSONObject(content.toString());
    }

    public static String getBvidFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1].split("\\?")[0];
    }

    public static int getPFromUrl(String url) {
        String[] parts = url.split("p=");
        System.out.println(parts);
        return Integer.parseInt(parts[parts.length - 1]);
    }

    public static String getSubtitleFromUrl(String url) throws Exception {
        String bvid = getBvidFromUrl(url);
        int p = getPFromUrl(url);
        List<Integer> cidList = biliPlayerList(bvid);
        int cid = cidList.get(p - 1);
        return biliSubtitle(bvid, cid);
    }
}