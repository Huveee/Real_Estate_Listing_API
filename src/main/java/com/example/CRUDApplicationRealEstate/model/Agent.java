package com.example.CRUDApplicationRealEstate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean deleted = false;  //For the soft deletion

    private String name;
    private String email;
    private String phone;

    @JsonIgnore // Prevent the endless loop
    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
    private List<Property> properties;
}

