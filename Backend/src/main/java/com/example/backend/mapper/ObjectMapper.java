package com.example.backend.mapper;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.dtos.Rating.AddRatingRequest;
import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.*;
import com.example.backend.enums.Availibility;
import com.example.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Component
public class ObjectMapper {
    public static BookingResponse mapBookingToBookingResponse(Booking booking){
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .spaceId(booking.getSpace().getSpaceId())
                .startDateTime(booking.getStartDateTime())
                .endDateTime(booking.getEndDateTime())
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
                .email(user.getEmail())
                .contactInfo(user.getContactInfo())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static Booking mapAddBookingRequestToBooking(AddBookingRequest addBookingRequest, double price , User client , Space space) {
        return Booking.builder()
                .startDateTime(addBookingRequest.getStartDateTime())
                .endDateTime(addBookingRequest.getEndDateTime())
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
                .availability(Availibility.NOT_RELEASED)
                //.spaceImage(addSpaceRequest.getSpaceImage())
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
                //.spaceImage(space.getSpaceImage())
                .availability(space.getAvailability())
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
    public static Rating mapRatingRequestToRating(AddRatingRequest addRatingRequest , Space space) {
        Rating rating = new Rating();
        rating.setScore(addRatingRequest.getScore());
        rating.setComment(addRatingRequest.getComment());
        rating.setDateAdded(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        rating.setSpace(space);
        rating.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return rating;
    }
//    public static ReportResponse mapReportToReportResponse(Report report) {
//        return new ReportResponse(
//                report.getReportId(),
//                report.getReportType(),
//                report.getReportStatus(),
//                report.getReportContent(),
//                report.getReportDateTime(),
//                report.getReporter(),
//                report.getReportedUser() != null ? Optional.ofNullable(mapUserToUserResponse(report.getReportedUser())) : null,
//                null
//        );
//    }
    public static Report mapReportRequestToReport(AddReportRequest addReportRequest, User user , Space space) {
        Report report = new Report();
        report.setReportType(addReportRequest.getReportType());
        report.setReportContent(addReportRequest.getReportContent());
        report.setReporter((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return switch (addReportRequest.getReportType()) {
            case USER -> {
                report.setReportedUser(user);
                yield report;
            }
            case SPACE -> {
                report.setReportedSpace(space);
                yield report;
            }
            default -> throw new IllegalArgumentException("Invalid report type");
        };
    }
}
