package com.example.backend.service;

import com.example.backend.dtos.Space.*;
import com.example.backend.entity.Space;
import com.example.backend.enums.Availibility;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

public interface SpaceService {

    SpaceResponse addSpace(AddSpaceRequest addSpaceRequest);

    SpaceResponse editSpace(EditSpaceRequest editSpaceRequest, String spaceId) throws AccessDeniedException;

    SpaceResponse deleteSpace(String id) throws AccessDeniedException;

    Page<SpaceResponse> searchSpaces(SpaceFilter filter , Pageable pageable);
//    @PostAuthorize("hasPermission(returnObject, 'READ')")
@PostAuthorize("hasPermission(returnObject, 'READ')")

SpaceResponse getSpace(String id) throws AccessDeniedException;

    SpaceResponse changeAvailability(String spaceId, Availibility availability) throws AccessDeniedException;

    Page<SpaceResponse> getMySpaces(SpaceFilter filter, Pageable pageable);

    Page<SpaceResponse> getAllSpaces(Pageable pageable);

    Boolean checkAvailabilityForBooking(String spaceId , Date startDate , Date endDate) throws AccessDeniedException;

    SpaceBookedDates getBookedDates(String spaceId);

    Page<SpaceResponse> getAllMySpaces(Pageable pageable);

    Boolean canDeleteSpace(String spaceId);
}
