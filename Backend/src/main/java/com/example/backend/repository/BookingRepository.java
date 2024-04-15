package com.example.backend.repository;

import com.example.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByClient_UserId(String userId);

    List<Booking> findBySpace_SpaceId(String spaceId);


    @Query("select b from Booking b where b.bookingId = ?1")
    Optional<Booking> findByBookingId(String bookingId);

    @Query("""
        SELECT b FROM Booking b
        WHERE b.startDateTime >= ?1
          AND b.endDateTime <= ?2
          AND (?3 IS NULL OR b.client.userId = ?3)
          AND (?4 IS NULL OR b.space.owner.userId = ?4)
          AND (?5 IS NULL OR b.space.spaceId = ?5)
       """)
    List<Booking> filterQuery(Date startDateTime, Date endDateTime, @Nullable String userId, @Nullable String ownerUserId, @Nullable String spaceId);

    @Transactional
    @Modifying
    @Query("delete from Booking b where b.bookingId = ?1")
    int deleteByBookingId(String bookingId);


}
