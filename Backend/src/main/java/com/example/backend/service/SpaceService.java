package com.example.backend.service;

import com.example.backend.dtos.Space.*;
import com.example.backend.entity.Space;
import com.example.backend.enums.Availibility;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface SpaceService {

    SpaceResponse addSpace(AddSpaceRequest addSpaceRequest);

    SpaceResponse editSpace(EditSpaceRequest editSpaceRequest, String spaceId);

    SpaceResponse deleteSpace(String id);

    Page<SpaceResponse> searchSpaces(SpaceFilter filter , Pageable pageable);

    SpaceResponse getSpace(String id);

    SpaceResponse changeAvailability(String spaceId, Availibility availability);

    Page<SpaceResponse> getMySpaces(SpaceFilter filter, Pageable pageable);

    Page<SpaceResponse> getAllSpaces(Pageable pageable);

    Boolean checkAvailabilityForBooking(String spaceId , Date startDate , Date endDate);

    SpaceBookedDates getBookedDates(String spaceId);
}
