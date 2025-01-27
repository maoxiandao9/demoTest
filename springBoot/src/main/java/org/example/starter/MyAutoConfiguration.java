package org.example.starter;

import org.example.config.MyProperties;
import org.example.service.MyService;
import org.example.service.impl.MyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyProperties.class)
public class MyAutoConfiguration {

    @Autowired
    private MyProperties myProperties;

    @Bean
    public MyService myService() {
        return new MyServiceImpl(myProperties);
    }
}
