package com.example.CRUDApplicationRealEstate.service;

import com.example.CRUDApplicationRealEstate.config.RequestContext;
import com.example.CRUDApplicationRealEstate.dto.OfferRequestDTO;
import com.example.CRUDApplicationRealEstate.model.Customer;
import com.example.CRUDApplicationRealEstate.model.Offer;
import com.example.CRUDApplicationRealEstate.model.Property;
import com.example.CRUDApplicationRealEstate.repo.CustomerRepository;
import com.example.CRUDApplicationRealEstate.repo.OfferRepository;
import com.example.CRUDApplicationRealEstate.repo.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final AuditLogService auditLogService;
    private final RequestContext requestContext;
    private final CustomerRepository customerRepository;
    private final PropertyRepository propertyRepository;


    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    public Offer getById(Long id) {
        return offerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
    }

    // HTTP 201 Created or 404 if related entities not found
    public Offer create(OfferRequestDTO dto) {
        Customer buyer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

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
                "Offer submitted for property ID: " + property.getId()
        );

        return saved;
    }

    // HTTP 204 No Content or 404 Not Found
    public void delete(Long id) { //SOFT DELETION
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));

        offer.setDeleted(true);
        offerRepository.save(offer);

        auditLogService.log(
                requestContext.getApiKey(),
                "SOFT_DELETE",
                "Offer",
                offer.getId(),
                "Offer marked as deleted"
        );
    }

    public List<Offer> getByProperty(Long propertyId) {
        return offerRepository.findByPropertyId(propertyId);
    }

}
