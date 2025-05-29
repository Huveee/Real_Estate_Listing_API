package com.example.CRUDApplicationRealEstate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private Double price;
    private String status; // Like Available, Under Offer, Sold

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    @JsonIgnoreProperties({"name", "email", "phone", "properties"}) // show just id
    private Agent agent;

    @Column(nullable = false)
    private boolean deleted = false;  //For deleted properties

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

