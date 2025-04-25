package com.demo.backend.repository;

import com.demo.backend.model.Invitation;
import com.demo.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByReceiver(User receiver);

}
