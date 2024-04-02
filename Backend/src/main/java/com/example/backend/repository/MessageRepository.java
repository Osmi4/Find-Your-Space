package com.example.backend.repository;

import com.example.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findByReceiver_UserId(String userId);

    Optional<Message> findByMessageId(String messageId);

    long deleteByMessageId(String messageId);

    List<Message> findByReceiver_UserIdOrderByMessageDateTimeAsc(String userId);


}
