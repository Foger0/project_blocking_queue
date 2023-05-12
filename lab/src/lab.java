import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lab {
    public static String[] extractNonNumericParts(String str) {
        List<String> parts = new ArrayList<String>();
        Pattern pattern = Pattern.compile("(\\d+)(\\D*)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            parts.add(matcher.group(2));
        }
        if (parts.size() == 0) {
            parts.add(str);
        } else if (str.lastIndexOf(parts.get(parts.size() - 1)) + parts.get(parts.size() - 1).length() < str.length()) {
            parts.add(str.substring(str.lastIndexOf(parts.get(parts.size() - 1)) + parts.get(parts.size() - 1).length()));
        }
        return parts.toArray(new String[0]);
    }

    public static void main(String[] args) {
        String inputStr = "1.国家安全的概念和内涵 2.国家安全的重要性和必要性 3.国家安全面临的威胁和挑战 4.国家安全的保障和维护 5.国家安全的法律制度和政策体系 6.国际合作与国家安全 7.全民参与和国家安全 8.信息化时代的国家安全 9.国家安全与个人权利的关系 10.未来国家安全的发展趋势和展望";
        String[] nonNumericParts = extractNonNumericParts(inputStr);
        for (String part : nonNumericParts) {
            System.out.println(part);
        }

    }

}
