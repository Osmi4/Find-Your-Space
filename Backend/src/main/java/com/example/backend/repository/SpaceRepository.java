package com.example.backend.repository;

import com.example.backend.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, String> {



    List<Space> findByOwner_UserId(String userId);

    long deleteBySpaceId(String spaceId);

    List<Space> findBySpaceSizeLessThanEqualAndSpaceSizeGreaterThanEqualAndSpacePriceLessThanEqualAndSpacePriceGreaterThanEqual(double spaceSize, double spaceSize1, double spacePrice, double spacePrice1);

    Optional<Space> findBySpaceId(String spaceId);


}
