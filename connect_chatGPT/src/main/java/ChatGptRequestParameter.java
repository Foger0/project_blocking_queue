import java.util.ArrayList;
import java.util.List;


public class ChatGptRequestParameter {
    private String model = "gpt-3.5-turbo";
    private List<ChatGptMessage> messages;

    public void addMessage(ChatGptMessage message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
    }
}
