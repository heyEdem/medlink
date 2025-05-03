package com.edem.medlink.service.availability;


import com.edem.medlink.dto.AvailabilityResponse;
import com.edem.medlink.dto.CreateAvailabilityRequest;
import com.edem.medlink.dto.GenericResponseMessage;
import com.edem.medlink.entities.Availability;
import com.edem.medlink.entities.User.Roles;
import com.edem.medlink.entities.User.User;
import com.edem.medlink.exception.UserNotFoundException;
import com.edem.medlink.repository.AvailabilityRepository;
import com.edem.medlink.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.edem.medlink.util.Validator.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GenericResponseMessage createAvailability(CreateAvailabilityRequest request, Authentication authentication) {
        log.info("Processing availability creation request: {}", request);

        // Get authenticated user
        User doctor = getDoctor(authentication);

        // Validate request
        validateRequest(request);

        // Check for overlapping availability
        if (availabilityRepository.findOverlappingSlots(doctor, request.startTime(), request.endTime())) {
            throw new IllegalArgumentException("Requested time slot overlaps with an existing availability.");
        }

        // Create and save availability
        Availability availability = Availability.builder()
                .doctor(doctor)
                .start_time(request.startTime())
                .end_time(request.endTime())
                .build();

        availabilityRepository.saveAndFlush(availability);
        log.info("Availability created for doctor: {}, startTime: {}, endTime: {}",
                doctor.getEmail(), request.startTime(), request.endTime());

        return new GenericResponseMessage("Availability created successfully");
    }

    @Override
    public List<AvailabilityResponse> getDoctorAvailabilities(UUID doctorId) {
        log.info("Fetching availabilities for doctorId: {}", doctorId);

        // Fetch doctor and verify role
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException("Doctor not found with ID: " + doctorId));
        if (doctor.getRole() != Roles.DOCTOR) {
            log.warn("User with ID {} is not a doctor", doctorId);
            throw new IllegalArgumentException("Specified user is not a doctor");
        }

        // Fetch future availabilities
        List<Availability> availabilities = availabilityRepository.findFutureAvailabilitiesByDoctor(doctor, LocalDateTime.now());
        log.info("Found {} future availabilities for doctor: {}", availabilities.size(), doctor.getEmail());

        // Map to DTO
        return availabilities.stream()
                .map(a -> new AvailabilityResponse(
                        a.getId().toString(),
                        a.getStart_time(),
                        a.getEnd_time()
                ))
                .collect(Collectors.toList());
    }

    private User getDoctor(Authentication authentication) {
        User user = userRepository.findUserByEmailAndVerified(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        if (user.getRole() != Roles.DOCTOR) {
            throw new IllegalStateException("Only doctors can create availability slots.");
        }
        return user;
    }

    private void validateRequest(CreateAvailabilityRequest request) {
        if (request.startTime().isAfter(request.endTime()) || request.startTime().isEqual(request.endTime())) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        if (request.startTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time cannot be in the past.");
        }
    }
}
