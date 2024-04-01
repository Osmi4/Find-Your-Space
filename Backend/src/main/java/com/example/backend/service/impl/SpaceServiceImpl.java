package com.example.backend.service.impl;

import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceFilter;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.entity.Booking;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.enums.Availibility;
import com.example.backend.mapper.ObjectMapper;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.SpaceService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
            throw new RuntimeException("Problem with adding space");
        }
        spaceRepository.save(space);
        return ObjectMapper.mapSpaceToSpaceResponse(space);
    }

    @Override
    public SpaceResponse editSpace(EditSpaceRequest editSpaceRequest , String spaceId) {
        Space space = spaceRepository.findBySpaceId(spaceId);
        if (space == null) {
            throw new RuntimeException("Space not found");
        }
        if (editSpaceRequest.getSpaceName() != null){
            space.setSpaceName(editSpaceRequest.getSpaceName());
        }
        if (editSpaceRequest.getSpaceLocation() != null){
            space.setSpaceLocation(editSpaceRequest.getSpaceLocation());
        }
        if (editSpaceRequest.getSpaceSize() != 0){
            space.setSpaceSize(editSpaceRequest.getSpaceSize());
        }
        if (editSpaceRequest.getSpacePrice() != 0){
            space.setSpacePrice(editSpaceRequest.getSpacePrice());
        }
        if(editSpaceRequest.getSpaceDescription() != null){
            space.setSpaceDescription(editSpaceRequest.getSpaceDescription());
        }
        if(editSpaceRequest.getOwnerId() != null){
            Optional<User> Owner = userRepository.findByUserId(editSpaceRequest.getOwnerId());
            if(Owner.isPresent()){
                space.setOwner(Owner.get());
            }
            else{
                throw new RuntimeException("Owner not found");
            }
        }
        spaceRepository.save(space);
        return ObjectMapper.mapSpaceToSpaceResponse(space);
    }

    @Override
    @Transactional
    public SpaceResponse deleteSpace(String id) {
        Space space = spaceRepository.findBySpaceId(id);
        if (space == null) {
            throw new RuntimeException("Space not found");
        }
        long deleted = spaceRepository.deleteBySpaceId(id);
        if (deleted == 0) {
            throw new RuntimeException("Problem with deleting space");
        }
        return ObjectMapper.mapSpaceToSpaceResponse(space);
    }
    @Override
    public List<SpaceResponse> searchSpaces(SpaceFilter filter) {
        List<Space> spaces = spaceRepository.findBySpaceSizeLessThanEqualAndSpaceSizeGreaterThanEqualAndSpacePriceLessThanEqualAndSpacePriceGreaterThanEqual(
                filter.getSpaceSizeUpperBound(), filter.getSpaceSizeLowerBound(), filter.getSpacePriceUpperBound(), filter.getSpacePriceLowerBound()
        );
        if(filter.getSpaceName()!=null){
            spaces.removeIf(space -> !space.getSpaceName().equals(filter.getSpaceName()));
        }
        if(filter.getSpaceLocation()!=null){
            spaces.removeIf(space -> !space.getSpaceLocation().equals(filter.getSpaceLocation()));
        }
        if(filter.getSpaceType()!=null){
            spaces.removeIf(space -> !space.getSpaceType().equals(filter.getSpaceType()));
        }
        if(filter.getAvailability()!=null){
            spaces.removeIf(space -> !space.getAvailibility().equals(filter.getAvailability()));
        }
        if(filter.getOwnerId()!=null){
            spaces.removeIf(space -> !space.getOwner().getUserId().equals(filter.getOwnerId()));
        }
        spaces.removeIf(space -> !checkSpaceAvailability(space, filter.getStartDate(), filter.getEndDate()));
        return spaces.stream().map(ObjectMapper::mapSpaceToSpaceResponse).toList();
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
        Space space = spaceRepository.findBySpaceId(id);
        if (space == null) {
            throw new RuntimeException("Space not found");
        }
        return ObjectMapper.mapSpaceToSpaceResponse(space);
    }
    @Override
    public SpaceResponse changeAvailability(String spaceId, Availibility availability) {
        Space space = spaceRepository.findBySpaceId(spaceId);
        if (space == null) {
            throw new RuntimeException("Space not found");
        }
        space.setAvailibility(availability);
        spaceRepository.save(space);
        return ObjectMapper.mapSpaceToSpaceResponse(space);
    }

    @Override
    public List<SpaceResponse> getMySpaces() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null){
            throw new RuntimeException("User not found");
        }
        return spaceRepository.findByOwner_UserId(user.getUserId()).stream().map(ObjectMapper::mapSpaceToSpaceResponse).toList();
    }

    @Override
    public List<SpaceResponse> getAllSpaces() {
        return spaceRepository.findAll().stream().map(ObjectMapper::mapSpaceToSpaceResponse).toList();
    }
}
