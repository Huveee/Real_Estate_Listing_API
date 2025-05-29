package com.example.CRUDApplicationRealEstate.dto;

import com.example.CRUDApplicationRealEstate.model.Agent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PropertyDetailDTO {  //Agent Info Included
    private Long id;
    private String title;
    private String description;
    private String location;
    private double price;
    private String status;
    private Agent agent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
