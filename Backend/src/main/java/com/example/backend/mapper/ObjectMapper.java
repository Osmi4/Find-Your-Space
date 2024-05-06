package com.example.backend.mapper;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.Booking;
import com.example.backend.entity.Message;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.enums.Availibility;
import com.example.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class ObjectMapper {
    public static BookingResponse mapBookingToBookingResponse(Booking booking){
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .spaceId(booking.getSpace().getSpaceId())
                .startDate(booking.getStartDateTime())
                .endDate(booking.getEndDateTime())
                .dateAdded(booking.getDateAdded())
                .dateUpdated(booking.getDateUpdated())
                .status(booking.getStatus())
                .client(mapUserToUserResponse(booking.getClient()))
                .owner(mapUserToUserResponse(booking.getSpace().getOwner()))
                .cost(booking.getCost())
                .build();
    }
    public static UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userEmail(user.getEmail())
                .contactInfo(user.getContactInfo())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static Booking mapAddBookingRequestToBooking(AddBookingRequest addBookingRequest, double price , User client , Space space) {
        return Booking.builder()
                .startDateTime(addBookingRequest.getStartDate())
                .endDateTime(addBookingRequest.getEndDate())
                .cost(price)
                .status(Status.INQUIRY)
                .description(addBookingRequest.getDescription())
                .dateAdded(new Date())
                .dateUpdated(new Date())
                .client(client)
                .space(space)
                .payment(null)
                .build();
    }
    public static Space mapAddSpaceRequestToSpace(AddSpaceRequest addSpaceRequest) {
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Space.builder()
                .spaceName(addSpaceRequest.getSpaceName())
                .spaceLocation(addSpaceRequest.getSpaceLocation())
                .spaceSize(addSpaceRequest.getSpaceSize())
                .spacePrice(addSpaceRequest.getSpacePrice())
                .availibility(Availibility.NOT_RELEASED)
                .spaceImage(addSpaceRequest.getSpaceImage())
                .spaceDescription(addSpaceRequest.getSpaceDescription())
                .spaceType(addSpaceRequest.getSpaceType())
                .dateAdded(new Date())
                .dateUpdated(new Date())
                .owner(owner)
                .ratings(new ArrayList<>())
                .build();
    }

    public static SpaceResponse mapSpaceToSpaceResponse(Space space) {
        return SpaceResponse.builder()
                .spaceId(space.getSpaceId())
                .spaceName(space.getSpaceName())
                .spaceLocation(space.getSpaceLocation())
                .spaceSize(space.getSpaceSize())
                .spacePrice(space.getSpacePrice())
                .spaceImage(space.getSpaceImage())
                .availability(space.getAvailibility())
                .owner(mapUserToUserResponse(space.getOwner()))
                .dateAdded(space.getDateAdded())
                .dateUpdated(space.getDateUpdated())
                .build();
    }
    public static MessageResponse mapMessageToMessageResponse(com.example.backend.entity.Message message) {
        return MessageResponse.builder()
                .messageId(message.getMessageId())
                .messageContent(message.getMessageContent())
                .senderId(message.getSender().getUserId())
                .receiverId(message.getReceiver().getUserId())
                .messageDateTime(message.getMessageDateTime())
                .messageDestinationEmail(message.getMessageDestinationEmail())
                .build();
    }

    public static Message mapAddMessageToMessage(AddMessage message, User sender, User receiver) {
        return Message.builder()
                .messageContent(message.getMessageContent())
                .messageDateTime(new Date())
                .messageDestinationEmail(message.getMessageDestinationEmail())
                .sender(sender)
                .receiver(receiver)
                .build();
    }

    public static List<Pair<Date, Date>> mapBookingsToBookedDates(List<Booking> bookings) {
        List<Pair<Date, Date>> bookedDates = new ArrayList<>();
        for (Booking booking : bookings) {
            bookedDates.add(new Pair<>(booking.getStartDateTime(), booking.getEndDateTime()));
        }
        return bookedDates;
    }
}
