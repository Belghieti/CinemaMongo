package com.demo.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private JwtChannelInterceptor jwtChannelInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Pour WebSocket standard (wss://...)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns(
                        "https://cinema-front-mngo.vercel.app",
                        "https://cinema-front-mngo-simons-projects-8e8f969f.vercel.app",
                        "http://localhost:3000"
                );

        // Pour SockJS fallback
        registry.addEndpoint("/ws-sockjs")
                .setAllowedOriginPatterns(
                        "https://cinema-front-mngo.vercel.app",
                        "https://cinema-front-mngo-simons-projects-8e8f969f.vercel.app",
                        "http://localhost:3000"
                )
                .withSockJS();
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtChannelInterceptor);
    }
}
