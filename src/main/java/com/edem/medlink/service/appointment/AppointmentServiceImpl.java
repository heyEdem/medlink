package com.edem.medlink.service.appointment;

import com.edem.medlink.dto.AppointmentResponse;
import com.edem.medlink.dto.GenericResponseMessage;
import com.edem.medlink.entities.Appointment.Appointment;
import com.edem.medlink.entities.Appointment.Status;
import com.edem.medlink.entities.Availability;
import com.edem.medlink.entities.User.User;
import com.edem.medlink.exception.UserNotFoundException;
import com.edem.medlink.repository.AppointmentProjection;
import com.edem.medlink.repository.AppointmentRepository;
import com.edem.medlink.repository.AvailabilityRepository;
import com.edem.medlink.repository.UserRepository;
import com.edem.medlink.service.availability.AvailabilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.edem.medlink.util.Validator.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
    private final AvailabilityService availabilityService;
    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public AppointmentResponse createAppointment(Authentication authentication, UUID availabilityID) {
        Availability availability = availabilityRepository.getAvailabilityById(availabilityID);
        String patientEmail = authentication.getName();
        User patient = userRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new UserNotFoundException("Patient not found"));

        User doctor = userRepository.findById(availability.getDoctor().getUserId()).orElseThrow(()-> new UserNotFoundException(DOCTOR_NOT_FOUND_MSG));

        // Create the Appointment from the Availability
        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .date(availability.getStart_time().toLocalDate())
                .start_time(availability.getStart_time().toLocalTime())
                .end_time(availability.getEnd_time().toLocalTime())
                .status(Status.PENDING)
                .build();

        // Mark the Availability as booked
        availability.setBooked(true);
        availabilityRepository.save(availability);

        // Save the Appointment
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Return the AppointmentResponse
        return new AppointmentResponse(
                patient.getFirstname() + " " + patient.getLastname(),
                doctor.getFirstname() + " " + doctor.getLastname(),
                appointment.getDate(),
                appointment.getStart_time(),
                appointment.getEnd_time(),
                appointment.getStatus()
        );
    }

    @Override
    public Page<AppointmentResponse> getUserAppointments(Authentication authentication, int page, int size) {
        User user = getUser(authentication);
        Pageable pageable = PageRequest.of(page, size);
        Page<AppointmentProjection> projections = appointmentRepository.getUserAppointments(user.getUserId(), pageable);
        return projections.map(projection -> new AppointmentResponse(
                projection.getPatientName(),
                projection.getDoctorName(),
                projection.getScheduledDate(),
                projection.getStartTime(),
                projection.getEndTime(),
                projection.getStatus()
        ));
    }

    @Override
    public GenericResponseMessage confirmAppointment(Authentication authentication, UUID appointmentId) {
        Appointment appointment = appointmentRepository.findAppointmentById(appointmentId);

        try {
            String authenticatedEmail = authentication.getName();

            if (!authenticatedEmail.equals(appointment.getDoctor().getEmail())) {
                throw new IllegalAccessException(UNAUTHORIZED_MSG);
            }

            appointment.setStatus(Status.CONFIRMED);
            appointmentRepository.save(appointment);

            return new GenericResponseMessage(APPOINTMENT_CONFIRMED);
        } catch (Exception e) {
            return new GenericResponseMessage("Failed to confirm appointment: " + e.getMessage());
        }
    }

    private User getUser(Authentication authentication) {
        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return userRepository.findUserByEmailAndVerified(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

}
