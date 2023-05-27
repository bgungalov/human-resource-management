package com.usm.UserManagmentService.Config;

import com.usm.UserManagmentService.Service.ServerWebSocketService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class ServerWebSocketConfig implements WebSocketConfigurer {

    /**
     * Register a WebSocketHandler with the given mapping paths and interceptors. The handler will be wrapped with a
     * (per-connection) WebSocketHandlerDecorator and a (per-request) HandshakeInterceptor.
     *
     * @param registry The WebSocketHandlerRegistry to configure.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/chat/{communicationId}").setAllowedOrigins("*");
    }

    /**
     * > This function creates a ServletServerContainerFactoryBean object, sets the maxTextMessageBufferSize and
     * maxBinaryMessageBufferSize properties to 8192, and returns the object
     *
     * @return A ServletServerContainerFactoryBean object.
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

    /**
     * Returns a WebSocketHandler object that will be used to handle all incoming WebSocket connections
     *
     * @return A WebSocketHandler object.
     */
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ServerWebSocketService();
    }
}