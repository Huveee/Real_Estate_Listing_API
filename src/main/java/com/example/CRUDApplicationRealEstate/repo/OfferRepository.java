package com.example.CRUDApplicationRealEstate.repo;

import com.example.CRUDApplicationRealEstate.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByPropertyId(Long propertyId);
    Optional<Offer> findByIdAndDeletedFalse(Long id);

}

