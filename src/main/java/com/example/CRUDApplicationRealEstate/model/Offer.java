package com.example.CRUDApplicationRealEstate.model;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id") // veya buyer_id
    private Customer buyer;

    private Double offerAmount;
    private String message;

    @Column(nullable = false)  //For soft delete
    private boolean deleted = false;


    private LocalDateTime submittedAt = LocalDateTime.now();

}

