package com.example.backend.service.impl;

import com.example.backend.dtos.Space.*;
import com.example.backend.entity.Booking;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.enums.Availibility;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.exception.UnauthorizedException;
import com.example.backend.mapper.ObjectMapper;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.SpaceService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public SpaceServiceImpl(SpaceRepository spaceRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.spaceRepository = spaceRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public SpaceResponse addSpace(AddSpaceRequest addSpaceRequest) {
        Space space = ObjectMapper.mapAddSpaceRequestToSpace(addSpaceRequest);
        if(space == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not correctly converted");
        }
        Space result = spaceRepository.save(space);
        if(result == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not saved");
        }
        SpaceResponse spaceResponse = ObjectMapper.mapSpaceToSpaceResponse(result);
        if(spaceResponse == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not correctly converted");
        }
        return spaceResponse;
    }

    @Override
    public SpaceResponse editSpace(EditSpaceRequest editSpaceRequest , String spaceId) {
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(spaceId);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", spaceId);
        }
        User spaceOwner = space.getOwner();
        if(!spaceOwner.getUserId().equals(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId())){
            throw new UnauthorizedException("You are not the owner of this space");
        }

        if (editSpaceRequest.getSpaceName() != null){
            space.setSpaceName(editSpaceRequest.getSpaceName());
        }
        if (editSpaceRequest.getSpaceLocation() != null){
            space.setSpaceLocation(editSpaceRequest.getSpaceLocation());
        }
        if (editSpaceRequest.getSpaceSize() > 0 ){
            space.setSpaceSize(editSpaceRequest.getSpaceSize());
        }
        if (editSpaceRequest.getSpacePrice() > 0){
            space.setSpacePrice(editSpaceRequest.getSpacePrice());
        }
        if(editSpaceRequest.getSpaceDescription() != null){
            space.setSpaceDescription(editSpaceRequest.getSpaceDescription());
        }
        var result = spaceRepository.save(space);
        if(result == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not updated");
        }
        return ObjectMapper.mapSpaceToSpaceResponse(space);
    }

    @Override
    @Transactional
    public SpaceResponse deleteSpace(String id) {
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(id);
        Space space = spaceOpt.orElse(null);
        if (space==null) {
            throw new ResourceNotFoundException("Space not found", "space", id);
        }
        if(!space.getOwner().getUserId().equals(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId())){
            throw new UnauthorizedException("You are not the owner of this space");
        }
        int deleted = spaceRepository.deleteBySpaceId(id);
        if (deleted == 0 || spaceRepository.findBySpaceId(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "space not deleted");
        }
        return ObjectMapper.mapSpaceToSpaceResponse(space);
    }
    @Override
    public Page<SpaceResponse> searchSpaces(SpaceFilter filter , Pageable pageable) {
        return doFilter(filter , Optional.empty() , pageable).map(ObjectMapper::mapSpaceToSpaceResponse);
    }
    private boolean checkSpaceAvailability(Space space, Date startDate, Date endDate) {
        List<Booking> bookings = bookingRepository.findBySpace_SpaceId(space.getSpaceId());
        for (Booking booking : bookings) {
            if (booking.getStartDateTime().before(startDate) && booking.getEndDateTime().after(endDate)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SpaceResponse getSpace(String id) {
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(id);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", id);
        }
        return ObjectMapper.mapSpaceToSpaceResponse(space);
    }
    @Override
    public SpaceResponse changeAvailability(String spaceId, Availibility availability) {
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(spaceId);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", spaceId);
        }
        space.setAvailibility(availability);
        spaceRepository.save(space);
        return ObjectMapper.mapSpaceToSpaceResponse(space);
    }
    @Override
    public Page<SpaceResponse> getMySpaces(SpaceFilter filter, Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //return doFilter(filter , Optional.ofNullable(user.getUserId()) , pageable).stream().map(ObjectMapper::mapSpaceToSpaceResponse).toList();
        return doFilter(filter , Optional.ofNullable(user.getUserId()) , pageable).map(ObjectMapper::mapSpaceToSpaceResponse);
    }

    @Override
    public Page<SpaceResponse> getAllSpaces(Pageable pageable) {
        //return spaceRepository.findAll().stream().map(ObjectMapper::mapSpaceToSpaceResponse).toList();
        return spaceRepository.findAll(pageable).map(ObjectMapper::mapSpaceToSpaceResponse);
    }

    @Override
    public SpaceBookedDates getBookedDates(String spaceId) {
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(spaceId);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", spaceId);
        }
        List<Booking> bookings = bookingRepository.findBySpace_SpaceId(spaceId);
        SpaceBookedDates spaceBookedDates = new SpaceBookedDates();
        spaceBookedDates.setBookedDates(ObjectMapper.mapBookingsToBookedDates(bookings));
        return spaceBookedDates;
    }

    public Boolean checkAvailabilityForBooking(String spaceId , Date startDate , Date endDate) {
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(spaceId);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", spaceId);
        }
        return checkSpaceAvailability(space, startDate, endDate);
    }
    private Page<Space> doFilter(SpaceFilter filter, Optional<String> userId , Pageable pageable) {
        Page<Space> spaces = spaceRepository.findSpacesByFilters(
                filter.getSpacePriceUpperBound(), filter.getSpacePriceLowerBound(),
                filter.getSpaceSizeUpperBound(), filter.getSpaceSizeLowerBound(),
                userId.orElse(null),
                filter.getSpaceName(),
                filter.getSpaceLocation(),
                filter.getSpaceType(),
                filter.getAvailability(),
                pageable);
            if(filter.getStartDate()!=null && filter.getEndDate()!=null){
                spaces = (Page<Space>) spaces.filter(space -> checkSpaceAvailability(space, filter.getStartDate(), filter.getEndDate()));
            }
        return spaces;
    }
}
