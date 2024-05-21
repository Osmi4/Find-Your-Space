package com.example.backend.repository;

import com.example.backend.entity.User;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.email = :email, u.contactInfo = :contactInfo, u.firstName = :firstName, u.lastName = :lastName WHERE u.userId = :id")
    int patchUser(@Param("id") String id,
                   @Param("email") String email,
                   @Param("contactInfo") String contactInfo,
                   @Param("firstName") String firstName,
                   @Param("lastName") String lastName);

    @Query("""
       select u from User u
       where (:email is null or LOWER(u.email) like LOWER(concat('%', :email, '%')))
       and (:firstName is null or LOWER(u.firstName) like LOWER(concat('%', :firstName, '%')))
       and (:lastName is null or LOWER(u.lastName) like LOWER(concat('%', :lastName, '%')))
       and (:contactInfo is null or LOWER(u.contactInfo) like LOWER(concat('%', :contactInfo, '%')))""")
    Page<User> findUsersByFilter(@Param("email") String email,
                                 @Param("firstName") String firstName,
                                 @Param("lastName") String lastName,
                                 @Param("contactInfo") String contactInfo,
                                 Pageable pageable);

}
