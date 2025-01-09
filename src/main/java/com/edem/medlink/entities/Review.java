package com.edem.medlink.entities;

import com.edem.medlink.entities.Appointment.Appointment;
import com.edem.medlink.entities.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int rating;

    @OneToOne
    private Appointment appointment;

    @OneToOne
    private User doctor;

    @OneToOne
    private User patient;

    private String comment;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Review review)) return false;
//        return getRating() == review.getRating() && Objects.equals(getId(), review.getId()) && Objects.equals(getAppointment(), review.getAppointment()) && Objects.equals(getDoctor(), review.getDoctor()) && Objects.equals(getPatient(), review.getPatient()) && Objects.equals(getComment(), review.getComment());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getRating(), getAppointment(), getDoctor(), getPatient(), getComment());
//    }
}
