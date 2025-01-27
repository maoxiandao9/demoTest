package org.example.service.impl;

import org.example.config.MyProperties;
import org.example.service.MyService;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService {
    private final MyProperties myProperties;

    public MyServiceImpl(MyProperties myProperties) {
        this.myProperties = myProperties;
    }

    @Override
    public String getName() {
        return myProperties.getName();
    }
}
