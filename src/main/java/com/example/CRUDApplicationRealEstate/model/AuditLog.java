package com.example.CRUDApplicationRealEstate.model;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp = LocalDateTime.now();

    private String apiKey;

    private String action; // create / update / delete
    private String entity; // örn: "Property"
    private Long entityId;

    @Column(length = 1000)
    private String details;
}

