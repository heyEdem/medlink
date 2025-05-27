package com.edem.medlink.repository;


import com.edem.medlink.entities.Appointment.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentProjection {
    String getPatientName();
    String getDoctorName();
    LocalDate getScheduledDate();
    LocalTime getStartTime();
    LocalTime getEndTime();
    Status getStatus();
}
