package org.example.langchain4jspringboot.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

@Service
public class ToolsService {

    /**
     * 告诉AI 什么对话才调用这个方法
     * @param name
     * @return
     */
    @Tool("某个地区有多少个名字的")
    public Integer changshaNameCount(
            // 告诉AI需要提取的信息
            @P("地区") String location,
            @P("姓名") String name) {

        // TODO 操作数据库等等
        System.out.println(name);
        System.out.println(location);

        // 结构
        return 10;
    }

    @Tool("退票")
    public String cancelBooking(
            // 告诉AI需要提取的信息
            @P("预订号") String bookingNumber,
            @P("姓名") String name) {

        // TODO 操作数据库等等
        System.out.println(name);
        System.out.println(bookingNumber);

        // 结构
        return "退票成功";
    }
}
