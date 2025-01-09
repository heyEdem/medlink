package com.edem.medlink.entities;

import com.edem.medlink.entities.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User doctor;

    @Column(nullable = false)
    private LocalDateTime start_time;

    @Column(nullable = false)
    private LocalDateTime end_time;

}
