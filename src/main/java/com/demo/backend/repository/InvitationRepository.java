package com.demo.backend.repository;

import com.demo.backend.model.Invitation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InvitationRepository extends MongoRepository<Invitation, String> {
    List<Invitation> findByReceiverId(String receiverId);
}
