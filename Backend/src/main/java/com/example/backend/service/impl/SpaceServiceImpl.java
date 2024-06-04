package com.example.backend.service.impl;

import com.example.backend.autoMapper.SpaceMapper;
import com.example.backend.dtos.Space.*;
import com.example.backend.entity.Booking;
import com.example.backend.entity.Image;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.enums.Availibility;
import com.example.backend.enums.PermissionType;
import com.example.backend.enums.SortType;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.exception.UnauthorizedException;
import com.example.backend.mapper.ObjectMapper;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.ImageRepository;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.PermissionService;
import com.example.backend.service.SpaceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ImageRepository imageRepository;
    @Autowired
    private PermissionService permissionService;

    public SpaceServiceImpl(SpaceRepository spaceRepository, UserRepository userRepository, BookingRepository bookingRepository, ImageRepository imageRepository) {
        this.spaceRepository = spaceRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public SpaceResponse addSpace(AddSpaceRequest addSpaceRequest) {
        //Space space = ObjectMapper.mapAddSpaceRequestToSpace(addSpaceRequest);
        Space space = SpaceMapper.INSTANCE.addSpaceRequestToSpace(addSpaceRequest, userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null));
        if (space == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not correctly converted");
        }
        Space result = spaceRepository.save(space);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not saved");
        }
        SpaceResponse spaceResponse = SpaceMapper.INSTANCE.spaceToSpaceResponse(result);
        if (spaceResponse == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not correctly converted");
        }

        permissionService.createPermissionFromListOfPermissions(result.getOwner().getEmail(), Space.class.getSimpleName(), result.getSpaceId(), PermissionServiceImpl.OWNER_PERMISSIONS);
        permissionService.createPermissionsForAdminsFromListOfPermissions(result.getOwner().getEmail(), Space.class.getSimpleName(), result.getSpaceId(), PermissionServiceImpl.ADMIN_PERMISSIONS);
        return spaceResponse;
    }

    @Transactional
    @Override
    public SpaceResponse editSpace(EditSpaceRequest editSpaceRequest, String spaceId) throws AccessDeniedException {
        permissionService.checkPermission(SecurityContextHolder.getContext().getAuthentication().getName(), Space.class.getSimpleName(), spaceId, PermissionType.UPDATE);
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(spaceId);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", spaceId);
        }
        User spaceOwner = space.getOwner();
//        if(!spaceOwner.getUserId().equals(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId())){
//            throw new UnauthorizedException("You are not the owner of this space");
//        }
        if (!spaceOwner.getUserId().equals(Objects.requireNonNull(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null)).getUserId())) {
            throw new UnauthorizedException("You are not the owner of this space");
        }
        int affectedRows = spaceRepository.updateSpace(spaceId, editSpaceRequest.getSpaceName(), editSpaceRequest.getSpaceLocation(), editSpaceRequest.getSpaceSize(), editSpaceRequest.getSpacePrice(), editSpaceRequest.getSpaceDescription());
        if (affectedRows == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not updated");
        }
        var result = spaceRepository.findBySpaceId(spaceId).orElse(null);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not updated");
        }
        return SpaceMapper.INSTANCE.spaceToSpaceResponse(result);
    }

    @Override
    @Transactional
    public SpaceResponse deleteSpace(String id) throws AccessDeniedException {
        permissionService.checkPermission(SecurityContextHolder.getContext().getAuthentication().getName(), Space.class.getSimpleName(), id, PermissionType.DELETE);

        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(id);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", id);
        }
        if (!space.getOwner().getUserId().equals(Objects.requireNonNull(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null)).getUserId())) {
            throw new UnauthorizedException("You are not the owner of this space");
        }
        if (!space.getBookings().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space has bookings");
        }
        //int deleted = spaceRepository.deleteBySpaceId(id);
        //int deleted = spaceRepository.deleteBySpaceId(id);
        //spaceRepository.delete(space);
        for (Image image : space.getImages()) {
            imageRepository.delete(image);
        }

        // Now delete the space
        spaceRepository.delete(space);
        if (spaceRepository.findBySpaceId(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "space not deleted");
        }
        return SpaceMapper.INSTANCE.spaceToSpaceResponse(space);
    }

    @Override
    public Page<SpaceResponse> searchSpaces(SpaceFilter filter, Pageable pageable) {
        return doFilter(filter, Optional.empty(), pageable).map(SpaceMapper.INSTANCE::spaceToSpaceResponse);
    }

    private boolean checkSpaceAvailability(Space space, Date startDate, Date endDate) throws AccessDeniedException {
        List<Booking> bookings = bookingRepository.findBySpace_SpaceId(space.getSpaceId());
        for (Booking booking : bookings) {
            if (booking.getStartDateTime().before(startDate) && booking.getEndDateTime().after(endDate)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SpaceResponse getSpace(String id) throws AccessDeniedException {
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(id);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", id);
        }
        return SpaceMapper.INSTANCE.spaceToSpaceResponse(space);
    }

    @Override
    public SpaceResponse changeAvailability(String spaceId, Availibility availability) throws AccessDeniedException {
        permissionService.checkPermission(SecurityContextHolder.getContext().getAuthentication().getName(), Space.class.getSimpleName(), spaceId, PermissionType.UPDATE);

        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(spaceId);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", spaceId);
        }
        int affectedRows = spaceRepository.updateSpaceAvailability(spaceId, availability);
        if (affectedRows == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Space not updated");
        }
        Space updatedSpace = spaceRepository.findBySpaceId(spaceId).orElse(null);
        return SpaceMapper.INSTANCE.spaceToSpaceResponse(updatedSpace);
    }

    @Override
    public Page<SpaceResponse> getMySpaces(SpaceFilter filter, Pageable pageable) {
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        if (user == null) {
            throw new UnauthorizedException("You are not logged in");
        }
        return doFilter(filter, Optional.ofNullable(user.getUserId()), pageable).map(SpaceMapper.INSTANCE::spaceToSpaceResponse);
        //return spaceRepository.findByOwner_UserId(user.getUserId(), pageable).map(SpaceMapper.INSTANCE::spaceToSpaceResponse);
    }

    @Override
    public Page<SpaceResponse> getAllSpaces(Pageable pageable) {

        return spaceRepository.findAll(pageable).map(SpaceMapper.INSTANCE::spaceToSpaceResponse);
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

    @Override
    public Page<SpaceResponse> getAllMySpaces(Pageable pageable) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        if (user == null) {
            throw new UnauthorizedException("You are not logged in");
        }
        return spaceRepository.findByOwner_UserId(user.getUserId(), pageable).map(SpaceMapper.INSTANCE::spaceToSpaceResponse);
    }

    public Boolean checkAvailabilityForBooking(String spaceId, Date startDate, Date endDate) throws AccessDeniedException {
        Optional<Space> spaceOpt = spaceRepository.findBySpaceId(spaceId);
        Space space = spaceOpt.orElse(null);
        if (space == null) {
            throw new ResourceNotFoundException("Space not found", "space", spaceId);
        }
        return checkSpaceAvailability(space, startDate, endDate);
    }

    private Page<Space> doFilter(SpaceFilter filter, Optional<String> userId, Pageable pageable) {
        Sort sort = Sort.unsorted();

        if (filter.getVariable() != null && filter.getType() != null) {
            Sort.Direction direction = filter.getType() == SortType.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;

            switch (filter.getVariable()) {
                case PRICE:
                    sort = Sort.by(direction, "spacePrice");
                    break;
                case SIZE:
                    sort = Sort.by(direction, "spaceSize");
                    break;
                default:
                    break;
            }
        }

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Space> spaces = spaceRepository.findSpacesByFilters(
                filter.getSpacePriceUpperBound(), filter.getSpacePriceLowerBound(),
                filter.getSpaceSizeUpperBound(), filter.getSpaceSizeLowerBound(),
                userId.orElse(null),
                filter.getSpaceName(),
                filter.getSpaceLocation(),
                filter.getSpaceType(),
                filter.getAvailability(),
                sortedPageable);

        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            spaces = (Page<Space>) spaces.filter(space -> {
                try {
                    return checkSpaceAvailability(space, filter.getStartDate(), filter.getEndDate());
                } catch (AccessDeniedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return spaces;
    }
}
