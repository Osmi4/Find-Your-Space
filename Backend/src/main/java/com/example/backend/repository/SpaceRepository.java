package com.example.backend.repository;

import com.example.backend.entity.Space;
import com.example.backend.enums.Availibility;
import com.example.backend.enums.SpaceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, String> {
    Page<Space> findByOwner_UserId(String userId, Pageable pageable);

    Optional<Space> findBySpaceId(String spaceId);

    @Query("""
    SELECT s FROM Space s
    WHERE s.spacePrice BETWEEN ?2 AND ?1
    AND s.spaceSize BETWEEN ?4 AND ?3
    AND (?5 IS NULL OR s.owner.userId = ?5)
    AND (?6 IS NULL OR s.spaceName LIKE %?6%)
    AND (?7 IS NULL OR s.spaceLocation LIKE %?7%)
    AND (?8 IS NULL OR s.spaceType = ?8)
    AND (?9 IS NULL OR s.availability = ?9)
    """)
    Page<Space> findSpacesByFilters(
            double spacePriceUp, double spacePriceLow,
            double spaceSizeUp, double spaceSizeLow,
            @Nullable String userId,
            @Nullable String spaceName,
            @Nullable String spaceLocation,
            @Nullable SpaceType spaceType,
            @Nullable Availibility availability,
            Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Space s where s.spaceId = ?1")
    int deleteBySpaceId(String spaceId);

    @Query("select s from Space s inner join s.bookings bookings where bookings.bookingId = ?1")
    Optional<Space> findByBookings_BookingId(String bookingId);
    @Transactional
    @Modifying
    @Query("UPDATE Space s SET " +
            "s.spaceName = COALESCE(:spaceName, s.spaceName), " +
            "s.spaceLocation = COALESCE(:spaceLocation, s.spaceLocation), " +
            "s.spaceSize = CASE WHEN :spaceSize IS NULL THEN s.spaceSize ELSE :spaceSize END, " +
            "s.spacePrice = CASE WHEN :spacePrice IS NULL THEN s.spacePrice ELSE :spacePrice END, " +
            "s.spaceDescription = COALESCE(:spaceDescription, s.spaceDescription) " +
            "WHERE s.spaceId = :id")
    int updateSpace(@Param("id") String id,
                    @Param("spaceName") String spaceName,
                    @Param("spaceLocation") String spaceLocation,
                    @Param("spaceSize") Double spaceSize,
                    @Param("spacePrice") Double spacePrice,
                    @Param("spaceDescription") String spaceDescription);

    @Transactional
    @Modifying
    @Query("UPDATE Space s SET " +
            "s.availability = :availability " +
            "WHERE s.spaceId = :id")
    int updateSpaceAvailability(@Param("id") String id,@Param("availability") Availibility availability);



}
