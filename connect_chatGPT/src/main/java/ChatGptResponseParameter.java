import java.util.List;@
public class ChatGptResponseParameter {
    private String id;
    private String object;
    private Long created;
    private String model;
    private Usage usage;
    private List<Choices> choices;
}


public class Usage {
    private Long prompt_tokens;
    private Long completion_tokens;
    private Long total_tokens;
}


public class Choices {
    private ChatGptMessage message;
    private String finish_reason;
    private Integer index;
}


public class ChatGptMessage {
    private String role;
    private String content;
}
