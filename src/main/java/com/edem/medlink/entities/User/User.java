package com.edem.medlink.entities.User;

import com.edem.medlink.entities.Appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(nullable = false)
    private String password;

    private String contact;

    private String profilePicture;


    //extra doctor fields
    private String clinic;

    private String specialty;

    private String qualification;

    private String bio;


    //extra patient fields
    private LocalDateTime dob;

    private String emergency_number;

    private String medical_history;


    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isVerified;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;


    @JsonBackReference
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Appointment> appointments;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(userId, user.userId) && Objects.equals(email, user.email) && Objects.equals(role, user.role) && Objects.equals(createdAt, user.createdAt) && Objects.equals(deletedAt, user.deletedAt) && Objects.equals(password, user.password) && Objects.equals(contact, user.contact) && Objects.equals(profilePicture, user.profilePicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, role, createdAt, deletedAt, password, contact, profilePicture);
    }
}
