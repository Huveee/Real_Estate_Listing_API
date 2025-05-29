package com.example.CRUDApplicationRealEstate.service;

import com.example.CRUDApplicationRealEstate.config.RequestContext;
import com.example.CRUDApplicationRealEstate.dto.PropertyDetailDTO;
import com.example.CRUDApplicationRealEstate.dto.PropertyResponseDTO;
import com.example.CRUDApplicationRealEstate.model.Property;
import com.example.CRUDApplicationRealEstate.repo.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final AuditLogService auditLogService;
    private final RequestContext requestContext;

    public Property create(Property property) {
        try {
            Property saved = propertyRepository.save(property);
            auditLogService.log(
                    requestContext.getApiKey(),
                    "CREATE",
                    "Property",
                    saved.getId(),
                    "Property created with title: " + saved.getTitle()
            );
            return saved;

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Property could not be created",
                    e
            );
        }
    }


    // GET /properties
    public List<PropertyResponseDTO> getAll() {
        return propertyRepository.findByDeletedFalse().stream()
                .map(p -> new PropertyResponseDTO(
                        p.getId(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getLocation(),
                        p.getPrice(),
                        p.getStatus(),
                        p.getCreatedAt(),
                        p.getUpdatedAt()
                )).toList();
    }



    // GET /properties/{id}
    public PropertyDetailDTO getById(Long id) {
        Property p = propertyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found or has been deleted"));

        return new PropertyDetailDTO(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getLocation(),
                p.getPrice(),
                p.getStatus(),
                p.getAgent(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }


    public Property update(Long id, Property newData) {
        Property prop = propertyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Property not found or has been deleted"));

        prop.setTitle(newData.getTitle());
        prop.setDescription(newData.getDescription());
        prop.setLocation(newData.getLocation());
        prop.setPrice(newData.getPrice());
        prop.setStatus(newData.getStatus());

        Property updated = propertyRepository.save(prop);

        auditLogService.log(
                requestContext.getApiKey(),
                "UPDATE",
                "Property",
                id,
                "Property updated"
        );

        return updated;
    }


    public void delete(Long id) {
        Property p = propertyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found or has already been deleted"));

        p.setDeleted(true);  //Sign as deleted
        propertyRepository.save(p); //Save the updated version

        auditLogService.log(
                requestContext.getApiKey(),
                "SOFT_DELETE",
                "Property",
                id,
                "Property marked as deleted"
        );
    }


    public List<Property> getByAgentId(Long agentId) {
        return propertyRepository.findByAgentIdAndDeletedFalse(agentId);

    }

    public List<Property> search(String location, Double minPrice, Double maxPrice) {
        return propertyRepository.findByDeletedFalse().stream()
                .filter(p -> location == null || p.getLocation().toLowerCase().contains(location.toLowerCase()))
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .toList();
    }

    public Property updateStatus(Long id, String status) {
        Property property = propertyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found or has been deleted"));

        property.setStatus(status);
        return propertyRepository.save(property);
    }



}
