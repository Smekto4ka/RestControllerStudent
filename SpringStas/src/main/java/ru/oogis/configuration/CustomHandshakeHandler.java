package ru.oogis.configuration;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    // Custom class for storing principal
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        // Generate principal with UUID as name
        Principal principal = request.getPrincipal();

        if (principal == null) {
            principal = new StompPrincipal();

            String uniqueName = UUID.randomUUID().toString();

            ((StompPrincipal) principal).setName(uniqueName);
        }

        return principal;
    }


}
