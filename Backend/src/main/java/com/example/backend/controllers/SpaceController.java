package com.example.backend.controllers;

import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceFilter;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.enums.Availibility;
import com.example.backend.exception.ErrorCreator;
import com.example.backend.service.SpaceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.processing.Generated;
import java.util.List;

@RestController
@RequestMapping("api/space")
public class SpaceController {
    private final SpaceService spaceService;
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping
    public ResponseEntity<?> addSpace(@Valid @RequestBody AddSpaceRequest addSpaceRequest) {
        try {
            SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
            return ResponseEntity.ok(spaceResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorCreator.createError(e.getMessage()));
        }
    }
    @PutMapping("/{spaceId}")
    public ResponseEntity<?> editSpace(@PathVariable String spaceId , @RequestBody EditSpaceRequest editSpaceRequest){
        try{
            return ResponseEntity.ok(spaceService.editSpace(editSpaceRequest , spaceId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorCreator.createError(e.getMessage()));
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllSpaces(){
        return ResponseEntity.ok(spaceService.getAllSpaces());
    }
    @GetMapping("/my_spaces")
    public ResponseEntity<?> getMySpaces(){
        try{
            return ResponseEntity.ok(spaceService.getMySpaces());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorCreator.createError(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpace(@PathVariable String id){
        try {
            return ResponseEntity.ok(spaceService.deleteSpace(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorCreator.createError(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSpace(@PathVariable String id){
        try {
            return ResponseEntity.ok(spaceService.getSpace(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorCreator.createError(e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchSpaces(@RequestBody SpaceFilter filter){
        return ResponseEntity.ok(spaceService.searchSpaces(filter));
    }
    @PutMapping("/{spaceId}/change_availability")
    public ResponseEntity<?> changeAvailability(@PathVariable String spaceId, @RequestBody Availibility availability){
        try {
            return ResponseEntity.ok(spaceService.changeAvailability(spaceId , availability));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorCreator.createError(e.getMessage()));
        }
    }

}
