package com.example.CRUDApplicationRealEstate.dto;

import lombok.Data;

@Data
public class OfferRequestDTO {
    private Long customerId;
    private Long propertyId;
    private Double offerAmount;
    private String message;
}

