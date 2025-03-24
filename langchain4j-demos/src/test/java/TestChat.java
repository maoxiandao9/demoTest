import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class TestChat {

    /**
     * 测试基本对话，openai
     */
    @Test
    void test01() {
        ChatLanguageModel model = OpenAiChatModel
                .builder()
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();

        String answer = model.chat("你好， 你是谁?");

        System.out.println(answer);

        UserMessage userMessage = UserMessage.userMessage("你好，我是amumu");
        ChatResponse response = model.chat(userMessage);
        AiMessage aiMessage = response.aiMessage();
        System.out.println(aiMessage.text());
        System.out.println("--------");

        ChatResponse response1 = model.chat(userMessage, aiMessage, UserMessage.userMessage("我是谁？"));
        AiMessage aiMessage1 = response1.aiMessage();
        System.out.println(aiMessage1.text());
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

    /**
     * 文生图
     */
    @Test
    void test() {
        WanxImageModel wanxImageModel = WanxImageModel
                .builder()
                .apiKey(System.getenv("ALI_API_KEY"))
                .modelName("wanx2.1-t2i-plus")
                .build();

        Response<Image> response = wanxImageModel.generate("校园");

        System.out.println(response.content().url());
    }
}
//sk-8bf0cce26e944f6fb41b02c0708a8d3d
//sk-3d51ab956e074eb2bef72a61efab56e0