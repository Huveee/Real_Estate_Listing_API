package com.example.CRUDApplicationRealEstate.repo;

import com.example.CRUDApplicationRealEstate.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}

