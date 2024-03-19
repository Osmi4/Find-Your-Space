package com.example.backend.repository;

import com.example.backend.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, String> {
}
