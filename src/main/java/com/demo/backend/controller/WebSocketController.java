package com.demo.backend.controller;

import com.demo.backend.model.ChatMessage;
import com.demo.backend.model.InvitationMessage;
import com.demo.backend.model.VideoSyncMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketController {

    @MessageMapping("/box/{boxId}/video-sync")
    @SendTo("/topic/box/{boxId}/video-sync")
    public VideoSyncMessage syncVideo(
            @DestinationVariable String boxId,
            VideoSyncMessage message
    ) {
        System.out.println("🔁 Sync video in box " + boxId + ": " + message.getAction() + " @ " + message.getTime());
        return message;
    }

    @MessageMapping("/box/{boxId}/chat")
    @SendTo("/topic/box/{boxId}/chat")
    public ChatMessage handleChat(
            @DestinationVariable String boxId,
            ChatMessage message
    ) {
        System.out.println("💬 Message dans box " + boxId + " de " + message.getSender() + ": " + message.getContent());
        return message;
    }

    @MessageMapping("/box/{boxId}/invite")
    @SendTo("/topic/box/{boxId}/invitations")
    public InvitationMessage handleInvitation(
            @DestinationVariable String boxId,
            InvitationMessage message
    ) {
        System.out.println("📨 Nouvelle invitation dans box " + boxId + " pour " + message.getInvitedUsername());
        return message;
    }
    @MessageMapping("/box/{boxId}/video-call")
    @SendTo("/topic/box/{boxId}/video-call")
    public Map<String, Object> handleVideoCall(@Payload Map<String, Object> signal) {
        return signal;
    }

    @MessageMapping("/box/{boxId}/call-users")
    @SendTo("/topic/box/{boxId}/call-users")
    public Map<String, Object> handleCallUsers(@Payload Map<String, Object> userInfo) {
        return userInfo;
    }

}
