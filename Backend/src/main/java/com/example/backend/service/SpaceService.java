package com.example.backend.service;

import com.example.backend.dtos.Space.*;
import com.example.backend.entity.Space;
import com.example.backend.enums.Availibility;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

public interface SpaceService {

    SpaceResponse addSpace(AddSpaceRequest addSpaceRequest);

    SpaceResponse editSpace(EditSpaceRequest editSpaceRequest, String spaceId);

    SpaceResponse deleteSpace(String id);

    List<SpaceResponse> searchSpaces(SpaceFilter filter);

    SpaceResponse getSpace(String id);

    SpaceResponse changeAvailability(String spaceId, Availibility availability);

    List<SpaceResponse> getMySpaces(SpaceFilter filter);

    List<SpaceResponse> getAllSpaces();

    Boolean checkAvailabilityForBooking(String spaceId , Date startDate , Date endDate);

    SpaceBookedDates getBookedDates(String spaceId);
}
