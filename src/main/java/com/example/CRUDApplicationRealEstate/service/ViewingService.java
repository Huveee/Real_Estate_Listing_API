package com.example.CRUDApplicationRealEstate.service;

import com.example.CRUDApplicationRealEstate.config.RequestContext;
import com.example.CRUDApplicationRealEstate.model.Customer;
import com.example.CRUDApplicationRealEstate.model.Property;
import com.example.CRUDApplicationRealEstate.model.Viewing;
import com.example.CRUDApplicationRealEstate.repo.CustomerRepository;
import com.example.CRUDApplicationRealEstate.repo.PropertyRepository;
import com.example.CRUDApplicationRealEstate.repo.ViewingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewingService {

    private final ViewingRepository viewingRepository;
    private final AuditLogService auditLogService;
    private final RequestContext requestContext;
    private final PropertyRepository propertyRepository;
    private final CustomerRepository customerRepository;


    public List<Viewing> getAll() {
        return viewingRepository.findByDeletedFalse();
    }

    public Viewing getById(Long id) {
        return viewingRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viewing not found or has been deleted"));
    }


    public Viewing create(Viewing viewing) {
        // Pull the property
        Property property = propertyRepository.findByIdAndDeletedFalse(viewing.getProperty().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found or has been deleted"));

        // Pull the customer
        Customer customer = customerRepository.findByIdAndDeletedFalse(viewing.getCustomer().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found or has been deleted"));

        //Set the relations
        viewing.setProperty(property);
        viewing.setCustomer(customer);
        viewing.setDeleted(false);

        // Save
        Viewing saved = viewingRepository.save(viewing);

        auditLogService.log(
                requestContext.getApiKey(),
                "CREATE",
                "Viewing",
                saved.getId(),
                "Viewing created"
        );

        return saved;
    }



    public void delete(Long id) {
        Viewing viewing = viewingRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Viewing not found or already deleted"));

        viewing.setDeleted(true);
        viewingRepository.save(viewing);

        auditLogService.log(
                requestContext.getApiKey(),
                "SOFT_DELETE",
                "Viewing",
                id,
                "Viewing marked as deleted"
        );
    }


    public List<Viewing> getUpcomingViewings() {
        return viewingRepository.findByDeletedFalseAndScheduledAtAfter(LocalDateTime.now());
    }

}
