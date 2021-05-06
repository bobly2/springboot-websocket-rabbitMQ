package com.example.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *
 * @Description:
 * @Date: 2021/4/30 9:26
 * @Version: 1.0
 */

@Configuration
public class WebSocketConfig {
    /**
     * 注入ServerEndpointExporter之后，
     *
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的websocket
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
