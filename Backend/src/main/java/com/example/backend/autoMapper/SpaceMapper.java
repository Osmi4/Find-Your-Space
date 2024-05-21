package com.example.backend.autoMapper;

import com.example.backend.dtos.Rating.RatingResponse;
import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.SpaceBookedDates;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.Booking;
import com.example.backend.entity.Rating;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import org.antlr.v4.runtime.misc.Pair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

@Mapper
public interface SpaceMapper {
    SpaceMapper INSTANCE = Mappers.getMapper(SpaceMapper.class);
    @Mappings({
            @Mapping(target = "availability", constant = "NOT_RELEASED"),
            @Mapping(target = "dateAdded", expression = "java(new java.util.Date())"),
            @Mapping(target = "dateUpdated", expression = "java(new java.util.Date())"),
            @Mapping(target = "owner", expression = "java(getAuthenticatedUser())"),
            @Mapping(target = "ratings", ignore = true),
            @Mapping(target = "images", ignore = true),
            @Mapping(target = "bookings", ignore = true),
            @Mapping(target = "spaceId", ignore = true)
    })
    Space addSpaceRequestToSpace(AddSpaceRequest addSpaceRequest);

    @Mappings({
            @Mapping(target = "owner", source = "owner"),
    })
    @Named("SpaceToSpaceResponse")
    SpaceResponse spaceToSpaceResponse(Space space);


    UserResponse userToUserResponse(User user);

    default User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
