package com.example.CRUDApplicationRealEstate.controller;

import com.example.CRUDApplicationRealEstate.model.AuditLog;
import com.example.CRUDApplicationRealEstate.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAll() {  //Get all the audit logs
        return ResponseEntity.ok(auditLogService.getAll());
    }
}

