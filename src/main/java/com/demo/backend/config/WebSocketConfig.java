package com.demo.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  //  private final JwtChannelInterceptor jwtChannelInterceptor;

  /*  public WebSocketConfig(JwtChannelInterceptor jwtChannelInterceptor) {
        this.jwtChannelInterceptor = jwtChannelInterceptor;
    }*/

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

  /*  @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wss").setAllowedOriginPatterns("https://cinema-front-mngo-simons-projects-8e8f969f.vercel.app").withSockJS();
    }*/
@Override
public void registerStompEndpoints(StompEndpointRegistry registry) {
    // Endpoint SockJS (fallback)
    registry.addEndpoint("/wss")
            .setAllowedOriginPatterns("*") // ou spécifie les domaines si tu veux plus de sécurité
            .withSockJS();

    // Endpoint WebSocket natif
    registry.addEndpoint("/wss")
            .setAllowedOriginPatterns("*"); // support natif sans SockJS
}

    /*@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtChannelInterceptor);
    }*/
}
