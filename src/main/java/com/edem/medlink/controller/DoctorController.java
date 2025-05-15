package com.edem.medlink.controller;
import com.edem.medlink.dto.AvailabilityResponse;
import com.edem.medlink.dto.CreateAvailabilityRequest;
import com.edem.medlink.dto.GenericResponseMessage;
import com.edem.medlink.service.availability.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private final AvailabilityService availabilityService;

    @Operation(
            summary = "Create availability slot for doctor",
            method = "POST"
    )
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/availability")
    public GenericResponseMessage createAvailability(
            @Valid @RequestBody CreateAvailabilityRequest request, Authentication authentication) {
        return availabilityService.createAvailability(request, authentication);
    }

    @Operation(
            summary = "Get future availabilities for a doctor",
            method = "GET"
    )
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @GetMapping("/{doctorId}/availabilities")
    public List<AvailabilityResponse> getDoctorAvailabilities(@PathVariable UUID doctorId) {
        log.info("Received request to fetch availabilities for doctorId: {}", doctorId);
        return availabilityService.getDoctorAvailabilities(doctorId);
    }

}