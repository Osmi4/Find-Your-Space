package com.example.backend.autoMapper;

import com.example.backend.dtos.Rating.AddRatingRequest;
import com.example.backend.dtos.Rating.RatingResponse;
import com.example.backend.entity.Rating;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper
public interface RatingMapper {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);
    @Mappings({
            @Mapping(target = "dateAdded", expression = "java(new java.util.Date())"),
            @Mapping(target = "user", expression = "java(getAuthenticatedUser())"),
            @Mapping(target = "ratingId", ignore = true)
    })
    Rating RatingRequestToRating(AddRatingRequest addRatingRequest, Space space);

    @Mapping(source = "dateAdded", target = "dateAdded")
    @Mapping(source = "space.spaceId", target = "spaceId")
    @Mapping(source = "user.userId", target = "userId")
    RatingResponse RatingToRatingResponse(Rating rating);

    default User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
