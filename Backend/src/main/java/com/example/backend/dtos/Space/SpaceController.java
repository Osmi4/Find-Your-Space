package com.example.backend.dtos.Space;

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
    public ResponseEntity<SpaceResponse> addSpace(@Valid @RequestBody AddSpaceRequest addSpaceRequest) {
            SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
            return ResponseEntity.ok(spaceResponse);
    }
    @PutMapping("/{spaceId}")
    public ResponseEntity<SpaceResponse> editSpace(@PathVariable String spaceId , @RequestBody EditSpaceRequest editSpaceRequest){
        return ResponseEntity.ok(spaceService.editSpace(editSpaceRequest , spaceId));
    }
    @GetMapping("/all")
    public ResponseEntity<List<SpaceResponse>> getAllSpaces(){
        return ResponseEntity.ok(spaceService.getAllSpaces());
    }
    //ma wiecej informacji bo to jest tylko dla ownera dla admina wszytskie tutaj
    @GetMapping("/my_spaces")
    public ResponseEntity<List<SpaceResponse>> getMySpaces(){
        return ResponseEntity.ok(spaceService.getMySpaces());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SpaceResponse> deleteSpace(@PathVariable String id){
        return ResponseEntity.ok(spaceService.deleteSpace(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceResponse> getSpace(@PathVariable String id){
        return ResponseEntity.ok(spaceService.getSpace(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SpaceResponse>> searchSpaces(@RequestBody SpaceFilter filter){
        return ResponseEntity.ok(spaceService.searchSpaces(filter));
    }
    @PutMapping("/{spaceId}/change_availability")
    public ResponseEntity<SpaceResponse> changeAvailability(@PathVariable String spaceId, @RequestBody Availibility availability){
        return ResponseEntity.ok(spaceService.changeAvailability(spaceId , availability));
    }

}
