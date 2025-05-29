package com.example.CRUDApplicationRealEstate.repo;

import com.example.CRUDApplicationRealEstate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByIdAndDeletedFalse(Long id);
    List<Property> findByDeletedFalse();
    List<Property> findByAgentIdAndDeletedFalse(Long agentId);



}

