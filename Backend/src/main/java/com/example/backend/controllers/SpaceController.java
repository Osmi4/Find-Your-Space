package com.example.backend.controllers;

import com.example.backend.dtos.Space.*;
import com.example.backend.enums.Availibility;
import com.example.backend.service.SpaceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/space")
@CrossOrigin(origins = "http://localhost:3000")
public class SpaceController {
    private final SpaceService spaceService;
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping
    public ResponseEntity<SpaceResponse> addSpace(@Valid @RequestBody AddSpaceRequest addSpaceRequest) {
            SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
            return ResponseEntity.ok(spaceResponse);
    }

    @PutMapping("/{spaceId}")
    public ResponseEntity<SpaceResponse> editSpace(@PathVariable String spaceId , @Valid @RequestBody EditSpaceRequest editSpaceRequest) throws AccessDeniedException {
        return ResponseEntity.ok(spaceService.editSpace(editSpaceRequest , spaceId));
    }
    @GetMapping("/all")
    public ResponseEntity<Page<SpaceResponse>> getAllSpaces(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(spaceService.getAllSpaces(pageable));
    }
    //ma wiecej informacji bo to jest tylko dla ownera dla admina wszytskie tutaj

    @GetMapping("/my-spaces")
    public ResponseEntity<Page<SpaceResponse>> getMySpaces(@Valid @RequestBody SpaceFilter filter, @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(spaceService.getMySpaces(filter , pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SpaceResponse> deleteSpace(@PathVariable String id) throws AccessDeniedException {
        return ResponseEntity.ok(spaceService.deleteSpace(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceResponse> getSpace(@PathVariable String id) throws AccessDeniedException {
        return ResponseEntity.ok(spaceService.getSpace(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SpaceResponse>> searchSpaces(@Valid @RequestBody SpaceFilter filter, @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(spaceService.searchSpaces(filter, pageable));
    }
    @PutMapping("/{spaceId}/change_availability")
    public ResponseEntity<SpaceResponse> changeAvailability(@PathVariable String spaceId, @RequestBody Availibility availability) throws AccessDeniedException {
        return ResponseEntity.ok(spaceService.changeAvailability(spaceId , availability));
    }
    @GetMapping("{spaceId}/check-availability")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable String spaceId, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate) throws AccessDeniedException {
        return ResponseEntity.ok(spaceService.checkAvailabilityForBooking(spaceId , startDate , endDate));
    }
    @GetMapping("/{spaceId}/booked-dates")
    public ResponseEntity<SpaceBookedDates> getBookedDates(@PathVariable String spaceId){
        return ResponseEntity.ok(spaceService.getBookedDates(spaceId));
    }

}
