package com.example.CRUDApplicationRealEstate.service;

import com.example.CRUDApplicationRealEstate.model.AuditLog;
import com.example.CRUDApplicationRealEstate.repo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(String apiKey, String action, String entity, Long entityId, String details) {
        AuditLog log = new AuditLog();
        log.setApiKey(apiKey);
        log.setAction(action);
        log.setEntity(entity);
        log.setEntityId(entityId);
        log.setDetails(details);
        auditLogRepository.save(log);
    }

    public List<AuditLog> getAll() {
        return auditLogRepository.findAll();
    }
}
