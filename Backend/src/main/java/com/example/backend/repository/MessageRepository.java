package com.example.backend.repository;

import com.example.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findByReceiver_UserId(String userId);

    Optional<Message> findByMessageId(String messageId);

    @Transactional
    @Modifying
    @Query("delete from Message m where m.messageId = ?1")
    int deleteByMessageId(String messageId);


    List<Message> findByReceiver_UserIdOrderByMessageDateTimeAsc(String userId);


}
