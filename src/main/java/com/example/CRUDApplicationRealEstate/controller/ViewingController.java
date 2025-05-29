package com.example.CRUDApplicationRealEstate.controller;

import com.example.CRUDApplicationRealEstate.model.Viewing;
import com.example.CRUDApplicationRealEstate.service.ViewingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viewings")
@RequiredArgsConstructor
public class ViewingController {

    private final ViewingService viewingService;

    @PostMapping
    public ResponseEntity<Viewing> create(@RequestBody Viewing viewing) {
        Viewing created = viewingService.create(viewing);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<Viewing>> getAll() {
        return ResponseEntity.ok(viewingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viewing> getById(@PathVariable Long id) {
        Viewing viewing = viewingService.getById(id); // throws 404 if not found
        return ResponseEntity.ok(viewing); // 200 OK
    }

    // get the close viewings
    @GetMapping("/upcoming")
    public ResponseEntity<List<Viewing>> getUpcoming() {
        return ResponseEntity.ok(viewingService.getUpcomingViewings());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        viewingService.delete(id); // throws 404 if not found
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

