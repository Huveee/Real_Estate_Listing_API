package com.example.CRUDApplicationRealEstate.model;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viewing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Property property;

    @ManyToOne
    private Customer customer;

    private LocalDateTime scheduledAt;
    private String status; // Pending, Approved, Cancelled

    @Column(nullable = false)  //For soft delete
    private boolean deleted = false;
}

