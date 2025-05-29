package com.example.CRUDApplicationRealEstate.service;

import com.example.CRUDApplicationRealEstate.config.RequestContext;
import com.example.CRUDApplicationRealEstate.model.Agent;
import com.example.CRUDApplicationRealEstate.repo.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;
    private final AuditLogService auditLogService;
    private final RequestContext requestContext;

    public List<Agent> getAll() {
        return agentRepository.findByDeletedFalse();
    }

    public Agent getById(Long id) {
        return agentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent not found"));
    }

    public Agent create(Agent agent) {
        Agent saved = agentRepository.save(agent);
        auditLogService.log(requestContext.getApiKey(), "CREATE", "Agent", saved.getId(), "Agent created");
        return saved;
    }

    public void delete(Long id) {  //Apply soft deletion
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent not found"));

        agent.setDeleted(true); // If it is deleted store it (deleted flag)
        agentRepository.save(agent);

        auditLogService.log(
                requestContext.getApiKey(),
                "SOFT_DELETE",
                "Agent",
                id,
                "Agent marked as deleted"
        );
    }


//Get the top three agents wıth the highest number of properties
    public List<Agent> getTopAgents() {
        return agentRepository.findAll().stream()
                .sorted((a1, a2) -> Integer.compare(
                        a2.getProperties() != null ? a2.getProperties().size() : 0,
                        a1.getProperties() != null ? a1.getProperties().size() : 0
                ))
                .limit(3)
                .toList();
    }

}

