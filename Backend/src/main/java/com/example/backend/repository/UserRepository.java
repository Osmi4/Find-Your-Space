package com.example.backend.repository;

import com.example.backend.entity.User;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User>findByUserId(@Param("id") String id);

    @Query("SELECT u FROM User u WHERE " +
            "(:userName IS NULL OR u.userName = :userName) AND " +
            "(:userEmail IS NULL OR u.email = :userEmail) AND " +
            "(:contactInfo IS NULL OR u.contactInfo = :contactInfo) AND " +
            "(:firstName IS NULL OR u.firstName = :firstName) AND " +
            "(:lastName IS NULL OR u.lastName = :lastName)"
    )
    List<User> findUsersByFilter(
            @Param("userName") @Nullable String userName,
            @Param("userEmail") @Nullable String userEmail,
            @Param("contactInfo") @Nullable String contactInfo,
            @Param("firstName") @Nullable String firstName,
            @Param("lastName") @Nullable String lastName
    );

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.userName = :userName, u.email = :userEmail, u.contactInfo = :contactInfo, u.firstName = :firstName, u.lastName = :lastName WHERE u.id = :id")
    int patchUser(@Param("id") String id,
                   @Param("userName") String userName,
                   @Param("userEmail") String userEmail,
                   @Param("contactInfo") String contactInfo,
                   @Param("firstName") String firstName,
                   @Param("lastName") String lastName);

}
