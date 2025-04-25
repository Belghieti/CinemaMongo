/*package com.demo.backend.config;


import com.demo.backend.repository.UserRepository;
import com.demo.backend.security.StompPrincipal;
import com.demo.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtChannelInterceptor(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && accessor.getCommand() != null) {
            List<String> authHeaders = accessor.getNativeHeader("Authorization");

            if (authHeaders != null && !authHeaders.isEmpty()) {
                String token = authHeaders.get(0).replace("Bearer ", "");
                String email = jwtService.extractUsername(token);

                if (email != null) {
                    var user = userRepository.findByEmail(email).orElse(null);
                    if (user != null) {
                        accessor.setUser(new StompPrincipal(user.getUsername()));
                        System.out.println("Utilisateur connecté : " + user.getUsername());
                    } else {
                        System.out.println("Aucun utilisateur trouvé avec l'email : " + email);
                    }

                }
            }
        }

        return message;
    }
}

 */
