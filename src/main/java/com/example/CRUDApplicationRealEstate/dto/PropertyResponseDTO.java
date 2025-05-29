package com.example.CRUDApplicationRealEstate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor  //There is No Agent
public class PropertyResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private double price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

