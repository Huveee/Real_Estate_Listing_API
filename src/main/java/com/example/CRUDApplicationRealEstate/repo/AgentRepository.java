package com.example.CRUDApplicationRealEstate.repo;


import com.example.CRUDApplicationRealEstate.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    List<Agent> findByDeletedFalse();

}

