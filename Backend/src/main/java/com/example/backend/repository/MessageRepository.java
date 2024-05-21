package com.example.backend.repository;

import com.example.backend.entity.Message;
import com.example.backend.enums.Availibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, String> {

    //List<Message> findByReceiver_UserId(String userId);


    Optional<Message> findByMessageId(String messageId);

    @Transactional
    @Modifying
    @Query("delete from Message m where m.messageId = ?1")
    int deleteByMessageId(String messageId);


    //List<Message> findByReceiver_UserIdOrderByMessageDateTimeAsc(String userId);


    @Query("select m from Message m where m.receiver.userId = ?1")
    Page<Message> findByReceiver_UserId(String userId, Pageable pageable);

    @Query("select m from Message m where m.receiver.userId = ?1 order by m.messageDateTime")
    Page<Message> findByReceiver_UserIdOrderByMessageDateTimeAsc(String userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET " +
            "m.messageContent = :messageContent " +
            "WHERE m.messageId = :id")
    int updateMessage(@Param("id") String id, @Param("messageContent") String messageContent);


}
