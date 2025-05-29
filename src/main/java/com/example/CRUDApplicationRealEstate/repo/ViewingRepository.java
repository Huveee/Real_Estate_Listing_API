package com.example.CRUDApplicationRealEstate.repo;

import com.example.CRUDApplicationRealEstate.model.Viewing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ViewingRepository extends JpaRepository<Viewing, Long> {
    Optional<Viewing> findByIdAndDeletedFalse(Long id);
    List<Viewing> findByDeletedFalse();
    List<Viewing> findByDeletedFalseAndScheduledAtAfter(LocalDateTime time);



}

