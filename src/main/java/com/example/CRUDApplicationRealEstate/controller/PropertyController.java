package com.example.CRUDApplicationRealEstate.controller;

import com.example.CRUDApplicationRealEstate.dto.PropertyDetailDTO;
import com.example.CRUDApplicationRealEstate.dto.PropertyResponseDTO;
import com.example.CRUDApplicationRealEstate.model.Property;
import com.example.CRUDApplicationRealEstate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    // GET /properties
    @GetMapping
    public ResponseEntity<List<PropertyResponseDTO>> getAll() {
        return ResponseEntity.ok(propertyService.getAll());
    }

    // GET /properties/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDetailDTO> getById(@PathVariable Long id) {
        PropertyDetailDTO detail = propertyService.getById(id); // throws 404 if not found
        return ResponseEntity.ok(detail); // 200 OK
    }

    @PostMapping
    public ResponseEntity<Property> create(@RequestBody Property property) {
        Property created = propertyService.create(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> update(@PathVariable Long id, @RequestBody Property property) {
        Property updated = propertyService.update(id, property); // throws 404 if not found
        return ResponseEntity.ok(updated); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        propertyService.delete(id); // throws 404 if not found
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Filtered search (location, minPrice)
    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchProperties(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ResponseEntity.ok(propertyService.search(location, minPrice, maxPrice));
    }

    // Update Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Property> updateStatus(@PathVariable Long id, @RequestBody String status) {
        Property updated = propertyService.updateStatus(id, status); // throws 404 if not found
        return ResponseEntity.ok(updated); // 200 OK
    }

    // Listings of one agent
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<Property>> getByAgent(@PathVariable Long agentId) {
        List<Property> properties = propertyService.getByAgentId(agentId); // optionally throws 404
        return ResponseEntity.ok(properties); // 200 OK
    }
}

