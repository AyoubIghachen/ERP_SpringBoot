package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
}
