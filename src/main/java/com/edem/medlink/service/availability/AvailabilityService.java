package com.edem.medlink.service.availability;

import com.edem.medlink.dto.AvailabilityResponse;
import com.edem.medlink.dto.CreateAvailabilityRequest;
import com.edem.medlink.dto.GenericResponseMessage;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface AvailabilityService {
    GenericResponseMessage createAvailability(CreateAvailabilityRequest request, Authentication authentication);
    List <AvailabilityResponse> getDoctorAvailabilities(UUID doctorId);
}