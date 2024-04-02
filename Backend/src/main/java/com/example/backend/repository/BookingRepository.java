package com.example.backend.repository;

import com.example.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByClient_UserId(String userId);

    List<Booking> findByStartDateTimeLessThanEqualAndClient_UserIdAndEndDateTimeGreaterThanEqual(Date startDateTime, String userId, Date endDateTime);

    List<Booking> findBySpace_SpaceId(String spaceId);

    List<Booking> findByBookingId(String bookingId);

    List<Booking> findBySpace_SpaceIdAndStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(String spaceId, Date startDateTime, Date endDateTime);


}
