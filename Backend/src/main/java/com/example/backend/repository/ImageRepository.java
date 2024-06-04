package com.example.backend.repository;

import com.example.backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {

    @Query("select i from Image i where i.imageId = ?1")
    Image findByImageId(String imageId);

    @Query("select i from Image i where i.space.spaceId = ?1")
    Image findBySpaceId(String spaceId);

    @Query("delete from Image i where i.imageId = ?1")
    int deleteByImageId(String imageId);
    //findBySpace_SpaceId -> return a list of images
    @Query("select i from Image i where i.space.spaceId = ?1")
    Page<Image> findBySpace_SpaceId(String spaceId);



}
