import java.io.IOException;
import java.util.Base64;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageChecker {
    private static String currentTitle = "";

    public static void main(String[] args) throws IOException {
        String url = "https://sso.dlut.edu.cn/cas/login?service=http%3A%2F%2Fjxgl.dlut.edu.cn%2Fstudent%2Fucas-sso%2Flogin";
        String username = "20211061069";
        String password = "dxy19660";

        while (true) {
            Document doc = Jsoup.connect(url)
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                    .get();
            String title = doc.title();
            String newUrl = doc.location();

            if (!title.equals(currentTitle)) {
                System.out.println("页面已更改！"+newUrl);
                currentTitle = title;
            }
            try {
                Thread.sleep(10000); // 每10秒检查一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}