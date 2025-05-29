package com.example.CRUDApplicationRealEstate.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //Automatic

    private String name;

    @Column(nullable = false) //For soft delete
    private boolean deleted = false;


}

