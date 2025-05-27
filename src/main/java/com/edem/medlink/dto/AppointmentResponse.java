package com.edem.medlink.dto;

import com.edem.medlink.entities.Appointment.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentResponse (
        String patient,
        String doctor,
        LocalDate scheduledDate,
        LocalTime startTime,
        LocalTime endTime,
        Status status
){
}
