package com.demo.backend.config;

import com.demo.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtService jwtService; // Ton service qui valide les JWT et retourne l'UserDetails

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> authHeaders = accessor.getNativeHeader("Authorization");
            if (authHeaders != null && !authHeaders.isEmpty()) {
                String authHeader = authHeaders.get(0);
                if (authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    try {
                        // Valide ton JWT ici comme dans tes filtres HTTP
                        UserDetails user = jwtService.loadUserByToken(token); // à adapter selon ton service
                        accessor.setUser(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
                        System.out.println("✅ Utilisateur WebSocket authentifié: " + user.getUsername());
                    } catch (Exception e) {
                        System.out.println("❌ JWT invalide WebSocket: " + e.getMessage());
                        throw new IllegalArgumentException("Token JWT invalide");
                    }
                }
            } else {
                System.out.println("❌ Pas d'en-tête Authorization dans le handshake STOMP");
            }
        }

        return message;
    }
}
