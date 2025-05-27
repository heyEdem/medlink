package com.edem.medlink.service.appointment;

import com.edem.medlink.dto.AppointmentResponse;
import com.edem.medlink.dto.GenericResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface AppointmentService {
    AppointmentResponse createAppointment(Authentication authentication, UUID availabilityId);
    Page<AppointmentResponse> getUserAppointments(Authentication authentication, int page, int size);

    //todo: sort appointment by status,
    GenericResponseMessage confirmAppointment(Authentication authentication, UUID appointmentId);
}
