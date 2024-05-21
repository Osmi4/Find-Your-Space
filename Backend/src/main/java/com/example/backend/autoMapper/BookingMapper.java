package com.example.backend.autoMapper;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.entity.Booking;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);
    @Mappings({
            @Mapping(source = "space.spaceId", target = "spaceId"),
            @Mapping(source = "space.owner", target = "owner"),
    })
    BookingResponse bookingToBookingResponse(Booking booking);

    @Mappings({
            @Mapping(source = "price", target = "cost"),
            @Mapping(target = "status", constant = "INQUIRY"),
            @Mapping(target = "dateAdded", expression = "java(new java.util.Date())"),
            @Mapping(target = "dateUpdated", expression = "java(new java.util.Date())"),
            @Mapping(source = "client", target = "client"),
            @Mapping(source = "space", target = "space"),
            @Mapping(target = "payment", ignore = true),
            @Mapping(target = "bookingId", ignore = true),
    })
    Booking addBookingRequestToBooking(AddBookingRequest addBookingRequest, double price, User client, Space space);
}
