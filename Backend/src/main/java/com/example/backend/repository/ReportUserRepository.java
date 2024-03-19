package com.example.backend.repository;

import com.example.backend.entity.ReportUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportUserRepository extends JpaRepository<ReportUser, String> {
}
