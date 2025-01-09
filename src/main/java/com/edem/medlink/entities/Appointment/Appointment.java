package com.edem.medlink.entities.Appointment;

import com.edem.medlink.entities.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "appointment_doctor")
    private User doctor;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "appointment_patient")
    private User patient;


    private LocalDate date;
    private LocalDateTime start_time;
    private LocalDateTime end_time;


    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Appointment that)) return false;
//        return Objects.equals(getId(), that.getId()) && Objects.equals(getDoctor(), that.getDoctor()) && Objects.equals(getPatient(), that.getPatient()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getStart_time(), that.getStart_time()) && Objects.equals(getEnd_time(), that.getEnd_time()) && getStatus() == that.getStatus();
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getDoctor(), getPatient(), getDate(), getStart_time(), getEnd_time(), getStatus());
//    }
}
