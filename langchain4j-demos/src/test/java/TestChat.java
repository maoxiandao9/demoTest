import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import org.junit.jupiter.api.Test;

public class TestChat {

    /**
     * 测试基本对话，openai
     */
    @Test
    void test() {
        ChatLanguageModel model = OpenAiChatModel
                .builder()
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();

        String answer = model.chat("你好， 你是谁?");

        System.out.println(answer);
    }

    /**
     * 测试基本对话，接入deepseek
     */
    @Test
    void test02() {
        ChatLanguageModel model = OpenAiChatModel
                .builder()
                .baseUrl("https://api.deepseek.com")
                .apiKey(System.getenv("DEEPSEEK_API_KEY"))
                .modelName("deepseek-reasoner")
                .build();

//        String apiKey = System.getenv("DEEPSEEK_API_KEY");
//        System.out.println("DEEPSEEK_API_KEY: " + apiKey);
        String answer = model.chat("你好， 你是谁?");

        System.out.println(answer);
    }

    /**
     * 测试基本对话，qwen-max
     */
    @Test
    void test03() {
        ChatLanguageModel model = QwenChatModel
                .builder()
//                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey(System.getenv("ALI_API_KEY"))
                .modelName("deepseek-r1")
                .build();

        String answer = model.chat("你好， 你是谁?");

        System.out.println(answer);
    }

    /**
     * 测试基本对话，ollama
     */
    @Test
    void test04() {
        ChatLanguageModel model = OllamaChatModel
                .builder()
                .baseUrl("http://localhost:11434")
//                .apiKey(System.getenv("ALI_API_KEY"))
                .modelName("deepseek-r1:1.5b")
                .build();

        String answer = model.chat("你好， 你是谁?");

        System.out.println(answer);
    }
}
//sk-8bf0cce26e944f6fb41b02c0708a8d3d
//sk-3d51ab956e074eb2bef72a61efab56e0