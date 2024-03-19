package com.example.backend.controllers;

import com.example.backend.service.ReportSpaceService;
import com.example.backend.service.ReportUserService;
import com.example.backend.service.impl.ReportUserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportUserService reportUserService;
    private final ReportSpaceService reportSpaceService;
    public ReportController(ReportUserService reportUserService, ReportSpaceService reportSpaceService) {
        this.reportUserService = reportUserService;
        this.reportSpaceService = reportSpaceService;
    }

}
