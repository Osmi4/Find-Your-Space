package com.example.backend.service.impl;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Booking.EditBookingRequest;
import com.example.backend.entity.Booking;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.enums.Status;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.mapper.ObjectMapper;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final SpaceRepository spaceRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, SpaceRepository spaceRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.spaceRepository = spaceRepository;
    }

    @Override
    public BookingResponse getBooking(String id) {
        return bookingRepository.findByBookingId(id).stream().map(ObjectMapper::mapBookingToBookingResponse).findFirst().orElse(null);
    }

    @Override
    public BookingResponse addBooking(AddBookingRequest addBookingRequest) {
        List<Booking> currentBookings = bookingRepository.findBySpace_SpaceId(addBookingRequest.getSpaceId());
        if (checkAvailability(currentBookings, addBookingRequest.getEndDate(), addBookingRequest.getStartDate())){
            throw new RuntimeException("Space is not available at this time");
        }
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(addBookingRequest.getSpaceId());
        Space space = spaceOpt.orElse(null);
        if(space == null) {
            throw new ResourceNotFoundException("Space not found", "space", addBookingRequest.getSpaceId());
        }
        double price = space.getSpacePrice()* (addBookingRequest.getEndDate().getTime() - addBookingRequest.getStartDate().getTime()) / 1000 / 60 / 60;
        User client = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User Owner = space.getOwner();
        Booking addedBooking = bookingRepository.save(ObjectMapper.mapAddBookingRequestToBooking(addBookingRequest , price , client , space));
        return ObjectMapper.mapBookingToBookingResponse(addedBooking);
    }

    @Override
    public BookingResponse updateBooking(EditBookingRequest editBookingRequest, String bookingId) {
        Booking bookingToUpdate = bookingRepository.findByBookingId(bookingId).stream().findFirst().orElse(null);
        if(bookingToUpdate == null || !bookingToUpdate.getStatus().equals(Status.INQUIRY) || !bookingToUpdate.getClient().getUserId().equals(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId())) {
            return null;
        }
        List<Booking> currentBookings = bookingRepository.findBySpace_SpaceId(editBookingRequest.getSpaceId());
        if(editBookingRequest.getSpaceId().equals(bookingToUpdate.getSpace().getSpaceId())) {
            currentBookings.remove(bookingToUpdate);
        }
        if (checkAvailability(currentBookings, editBookingRequest.getEndDate(), editBookingRequest.getStartDate()))
            return null;
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(editBookingRequest.getSpaceId());
        Space space = spaceOpt.orElse(null);
        if(space == null) {
            throw new ResourceNotFoundException("Space not found", "space", editBookingRequest.getSpaceId());
        }
        double price = space.getSpacePrice()* (editBookingRequest.getEndDate().getTime() - editBookingRequest.getStartDate().getTime()) / 1000 / 60 / 60;
        bookingToUpdate.setStartDateTime(editBookingRequest.getStartDate());
        bookingToUpdate.setEndDateTime(editBookingRequest.getEndDate());
        bookingToUpdate.setCost(price);
        bookingToUpdate.setSpace(space);
        Booking updatedBooking = bookingRepository.save(bookingToUpdate);
        return ObjectMapper.mapBookingToBookingResponse(updatedBooking);
    }

    private static boolean checkAvailability(List<Booking> currentBookings, Date editBookingRequest, Date editBookingRequest1) {
        for (Booking booking : currentBookings) {
            if (booking.getStartDateTime().before(editBookingRequest) && booking.getEndDateTime().after(editBookingRequest1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BookingResponse deleteBooking(String id) {
        Booking bookingToDelete = bookingRepository.findByBookingId(id).stream().findFirst().orElse(null);
        if(bookingToDelete == null || !bookingToDelete.getStatus().equals(Status.INQUIRY) || !bookingToDelete.getClient().getUserId().equals(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "booking is not found or not owned by user or not in inquiry status");
        }
        bookingRepository.delete(bookingToDelete);
        return ObjectMapper.mapBookingToBookingResponse(bookingToDelete);
    }

    @Override
    public List<BookingResponse> getMyBookings() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "user not log in");
        }
        else{
            List<Booking> bookings = bookingRepository.findByClient_UserId(user.getUserId());
            return bookings.stream().map(ObjectMapper::mapBookingToBookingResponse).toList();
        }
    }

    @Override
    public List<BookingResponse> getSearchMyBookings(BookingFilter filter) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "user not log in");
        }
        List<BookingResponse> bookings = bookingRepository.findByStartDateTimeLessThanEqualAndClient_UserIdAndEndDateTimeGreaterThanEqual(
                filter.getStartDate(), user.getUserId(), filter.getEndDate()).stream().map(ObjectMapper::mapBookingToBookingResponse).toList();

        if(filter.getSpaceId() != null) {
            bookings = bookings.stream().filter(booking -> booking.getSpaceId().equals(filter.getSpaceId())).toList();
        }
        if(filter.getStatus() != null) {
            bookings = bookings.stream().filter(booking -> booking.getStatus().equals(filter.getStatus())).toList();
        }
        if(filter.getOwnerId() != null) {
            bookings = bookings.stream().filter(booking -> booking.getOwner().getUserId().equals(filter.getOwnerId())).toList();
        }
        return bookings;
    }

    @Override
    public List<BookingResponse> getBookingForSpace(String spaceId, BookingFilter filter) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "user not log in");
        }
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(spaceId);
        Space space = spaceOpt.orElse(null);
        if(space == null || !space.getOwner().getUserId().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "space not found or not owned by user");
        }
        List<BookingResponse> bookings = bookingRepository.findBySpace_SpaceIdAndStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(
                spaceId, filter.getStartDate(), filter.getEndDate()).stream().map(ObjectMapper::mapBookingToBookingResponse).toList();
        if(filter.getStatus() != null) {
            bookings = bookings.stream().filter(booking -> booking.getStatus().equals(filter.getStatus())).toList();
        }
        return bookings;
    }
}
