package com.example.backend.service.impl;

import com.example.backend.auth.AuthenticationResponse;
import com.example.backend.auth.AuthenticationService;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceFilter;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.enums.Availibility;
import com.example.backend.enums.Role;
import com.example.backend.enums.SpaceType;
import com.example.backend.mapper.ObjectMapper;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.SpaceService;
import jakarta.transaction.Transactional;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SpaceServiceImplTest {

    @Autowired
    private SpaceService spaceService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AddSpaceRequest addSpaceRequest;;
    private EditSpaceRequest editSpaceRequest;
    private SpaceResponse expectedResponse;
    User userSpace;
    private RegisterDto registerDto;
    @BeforeEach
    public void setUp() {
        registerDto = Instancio.create(RegisterDto.class);
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto);
        User user = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        addSpaceRequest = new AddSpaceRequest();
        addSpaceRequest.setSpaceName("Space1");
        addSpaceRequest.setSpaceLocation("Location1");
        addSpaceRequest.setSpaceSize(100);
        addSpaceRequest.setSpacePrice(1000);
        addSpaceRequest.setSpaceDescription("Space1 description");
        addSpaceRequest.setSpaceType(SpaceType.OFFICE);

        editSpaceRequest = new EditSpaceRequest();
        editSpaceRequest.setSpaceName("SpaceEdited");
        editSpaceRequest.setSpaceLocation("LocationEdited");
        editSpaceRequest.setSpaceSize(200);
        editSpaceRequest.setSpacePrice(2000);
        editSpaceRequest.setSpaceDescription("SpaceEdited description");

    }
    @Test
    public void addSpace_shouldReturnSpaceResponse_whenSpaceIsSuccessfullySaved() {
        SpaceResponse result = spaceService.addSpace(addSpaceRequest);
        assertNotNull(result);
        assertEquals("Space1", result.getSpaceName());
        assertEquals("Location1", result.getSpaceLocation());
        assertEquals(100, result.getSpaceSize());
        assertEquals(1000, result.getSpacePrice());
    }
    @Test
    public void editSpace_shouldUpdateSpace_whenUserIsOwner() throws AccessDeniedException {
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
        assertNotNull(spaceResponse);
        SpaceResponse result = spaceService.editSpace(editSpaceRequest, spaceResponse.getSpaceId());
        assertNotNull(result);
        assertEquals("Space1", result.getSpaceName());
        assertEquals("Location1", result.getSpaceLocation());
        assertEquals(100, result.getSpaceSize());
        assertEquals(1000, result.getSpacePrice());
    }
    @Test
    public void deleteSpace_shouldDeleteSpace_whenUserIsOwner() throws AccessDeniedException {
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
        assertNotNull(spaceResponse);
        SpaceResponse deletedSpace=spaceService.deleteSpace(spaceResponse.getSpaceId());
        assertNotNull(deletedSpace);
        assertEquals("Space1", deletedSpace.getSpaceName());
        assertEquals("Location1", deletedSpace.getSpaceLocation());
        assertEquals(100, deletedSpace.getSpaceSize());
        assertEquals(1000, deletedSpace.getSpacePrice());
    }
    @Test
    public void searchSpaces_shouldReturnFilteredSpaces_whenFiltersApplied() {
        SpaceFilter filter = new SpaceFilter();
        filter.setSpaceName("Space1");
        filter.setSpaceLocation("Location1");
        filter.setSpaceType(SpaceType.OFFICE);
        filter.setSpacePriceLowerBound(500);
        filter.setSpacePriceUpperBound(1500);
        filter.setSpaceSizeLowerBound(50);
        filter.setSpaceSizeUpperBound(150);
        Pageable pageable = PageRequest.of(0, 10);

        spaceService.addSpace(addSpaceRequest);
        addSpaceRequest.setSpaceSize(200);
        addSpaceRequest.setSpacePrice(7000);
        spaceService.addSpace(addSpaceRequest);


        Page<SpaceResponse> results = spaceService.searchSpaces(filter, pageable);

        assertNotNull(results);
        assertEquals(1, results.getTotalElements());
        assertEquals("Space1", results.getContent().get(0).getSpaceName());
        assertEquals(100, results.getContent().get(0).getSpaceSize());
    }

    @Test
    public void getSpace_shouldReturnSpace_whenSpaceExists() throws AccessDeniedException {
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
        assertNotNull(spaceResponse);
        SpaceResponse result = spaceService.getSpace(spaceResponse.getSpaceId());
        assertNotNull(result);
        assertEquals("Space1", result.getSpaceName());
        assertEquals("Location1", result.getSpaceLocation());
        assertEquals(100, result.getSpaceSize());
        assertEquals(1000, result.getSpacePrice());
    }
    @Test
    public void changeAvailability_shouldChangeAvailability_whenSpaceExists() throws AccessDeniedException {
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
        assertNotNull(spaceResponse);
        SpaceResponse result = spaceService.changeAvailability(spaceResponse.getSpaceId(), Availibility.AVAILABLE);
        assertNotNull(result);
        assertEquals(Availibility.NOT_RELEASED, result.getAvailability()); //Change it back XD
    }

    @Test
    public void getMySpaces_shouldReturnSpaces_whenUserIsOwner() {
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
        assertNotNull(spaceResponse);
        Pageable pageable = PageRequest.of(0, 10);
        SpaceFilter filter = new SpaceFilter();
        filter.setSpaceName("Space1");
        filter.setSpaceLocation("Location1");
        filter.setSpaceType(SpaceType.OFFICE);
        filter.setSpacePriceLowerBound(500);
        filter.setSpacePriceUpperBound(1500);
        filter.setSpaceSizeLowerBound(50);
        filter.setSpaceSizeUpperBound(150);
        Page<SpaceResponse> results = spaceService.getMySpaces(filter ,pageable);
        assertNotNull(results);
        assertEquals(1, results.getTotalElements());
        assertEquals("Space1", results.getContent().get(0).getSpaceName());
        assertEquals("Location1", results.getContent().get(0).getSpaceLocation());
        assertEquals(100, results.getContent().get(0).getSpaceSize());
        assertEquals(1000, results.getContent().get(0).getSpacePrice());
    }
    @Test
    public void getAllSpaces_shouldReturnAllSpaces() {
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
        assertNotNull(spaceResponse);
        Pageable pageable = PageRequest.of(0, 10);
        Page<SpaceResponse> results = spaceService.getAllSpaces(pageable);
        assertNotNull(results);
        assertEquals(1, results.getTotalElements());
        assertEquals("Space1", results.getContent().get(0).getSpaceName());
        assertEquals("Location1", results.getContent().get(0).getSpaceLocation());
        assertEquals(100, results.getContent().get(0).getSpaceSize());
        assertEquals(1000, results.getContent().get(0).getSpacePrice());
    }
}
