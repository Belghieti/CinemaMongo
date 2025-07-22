package com.demo.backend.config;

import com.demo.backend.repository.UserRepository;
import com.demo.backend.security.StompPrincipal;
import com.demo.backend.service.JwtService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.List;

/*
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
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null) {
            // Vérifier que c'est un message CONNECT (lors de la connexion initiale)
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
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
                    } else {
                        System.out.println("Token JWT invalide ou expiré");
                    }
                } else {
                    System.out.println("Aucun header Authorization trouvé dans la connexion WebSocket");
                    // Optionnel : tu peux décider de refuser la connexion ici
                }
            }
        }
        return message;
    }

}
*/

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // On ignore la vérification du token JWT pour les connexions WebSocket
        // et on accepte les connexions sans authentification.
        // Cette approche est utilisée pour permettre aux clients de se connecter
        // sans avoir à fournir un token JWT, ce qui est utile pour les tests ou
        // les environnements de développement où l'authentification n'est pas nécessaire.

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null) {
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                // On ignore la vérification du token, on accepte tout le monde
                accessor.setUser(new StompPrincipal("anonymous"));
                System.out.println("Connexion WebSocket acceptée sans authentification");
            }
        }
        return message;
    }
}
