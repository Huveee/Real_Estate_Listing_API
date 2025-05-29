package com.example.CRUDApplicationRealEstate.controller;

import com.example.CRUDApplicationRealEstate.config.RequestContext;
import com.example.CRUDApplicationRealEstate.dto.OfferRequestDTO;
import com.example.CRUDApplicationRealEstate.model.Customer;
import com.example.CRUDApplicationRealEstate.model.Offer;
import com.example.CRUDApplicationRealEstate.model.Property;
import com.example.CRUDApplicationRealEstate.repo.CustomerRepository;
import com.example.CRUDApplicationRealEstate.repo.OfferRepository;
import com.example.CRUDApplicationRealEstate.repo.PropertyRepository;
import com.example.CRUDApplicationRealEstate.service.AuditLogService;
import com.example.CRUDApplicationRealEstate.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final PropertyRepository propertyRepository;
    private final OfferRepository offerRepository;
    private final CustomerRepository customerRepository;
    private final AuditLogService auditLogService;
    private final RequestContext requestContext;


    @GetMapping
    public ResponseEntity<List<Offer>> getAll() {
        return ResponseEntity.ok(offerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getById(@PathVariable Long id) {
        Offer offer = offerService.getById(id); // throws 404 if not found
        return ResponseEntity.ok(offer); // 200 OK
    }


    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody OfferRequestDTO dto) {
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        Customer buyer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Offer offer = new Offer();
        offer.setBuyer(buyer);
        offer.setProperty(property);
        offer.setOfferAmount(dto.getOfferAmount());
        offer.setMessage(dto.getMessage());
        offer.setSubmittedAt(LocalDateTime.now());

        Offer saved = offerRepository.save(offer);

        auditLogService.log(
                requestContext.getApiKey(),
                "CREATE",
                "Offer",
                saved.getId(),
                "Offer created for property ID " + property.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 201 Created
    }



    // All offers for the specific property listing
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Offer>> getOffersByProperty(@PathVariable Long propertyId) {
        return ResponseEntity.ok(offerService.getByProperty(propertyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        offerService.delete(id);  // throws 404 if not found
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody OfferRequestDTO dto) {
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        Customer buyer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Offer offer = offerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found or is deleted"));

        offer.setBuyer(buyer);
        offer.setOfferAmount(dto.getOfferAmount());
        offer.setMessage(dto.getMessage());
        offer.setProperty(property);

        auditLogService.log(
                requestContext.getApiKey(),
                "UPDATE",
                "Offer",
                offer.getId(),
                "Offer updated for property ID: " + property.getId()
        );

        return ResponseEntity.ok(offerRepository.save(offer)); // 200 OK
    }


}

