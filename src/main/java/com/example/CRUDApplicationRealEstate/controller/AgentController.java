package com.example.CRUDApplicationRealEstate.controller;

import com.example.CRUDApplicationRealEstate.model.Agent;
import com.example.CRUDApplicationRealEstate.model.Property;
import com.example.CRUDApplicationRealEstate.service.AgentService;
import com.example.CRUDApplicationRealEstate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;
    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<Agent>> getAll() {  //Get all the agents
        return ResponseEntity.ok(agentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> getById(@PathVariable Long id) { //Get the agent wıth id
        Agent agent = agentService.getById(id); // already throws 404 if not found
        return ResponseEntity.ok(agent);
    }

    @PostMapping
    public ResponseEntity<Agent> create(@RequestBody Agent agent) { //Create new agent
        Agent created = agentService.create(agent);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201 Created
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {  //Delete the agent
        agentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Get Top 3 agents with the most listings
    @GetMapping("/top")
    public ResponseEntity<List<Agent>> getTopAgents() {
        return ResponseEntity.ok(agentService.getTopAgents());
    }

    @GetMapping("/{agentId}/properties")
    public ResponseEntity<List<Property>> getAgentProperties(@PathVariable Long agentId) {
        // OPTIONAL: validate agent exists before listing properties
        if (agentService.getById(agentId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent not found");
        }

        List<Property> properties = propertyService.getByAgentId(agentId);
        return ResponseEntity.ok(properties); // 200 OK
    }

}

