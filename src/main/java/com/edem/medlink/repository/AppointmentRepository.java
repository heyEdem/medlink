package com.edem.medlink.repository;

import com.edem.medlink.dto.AppointmentResponse;
import com.edem.medlink.entities.Appointment.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT " +
            "CONCAT(a.patient.firstname, ' ', a.patient.lastname) AS patientName, " +
            "CONCAT(a.doctor.firstname, ' ', a.doctor.lastname) AS doctorName, " +
            "a.date AS scheduledDate, " +
            "a.start_time AS startTime, " +
            "a.end_time AS endTime, " +
            "a.status AS status " +
            "FROM Appointment a " +
            "WHERE a.patient.userId = :userId OR a.doctor.userId = :userId")
    Page<AppointmentProjection> getUserAppointments(UUID userId, Pageable pageable);

    Appointment findAppointmentById(UUID appointmentId);
}
