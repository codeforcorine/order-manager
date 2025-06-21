package com.technical.evaluation.orders.features.client.repository;

import com.technical.evaluation.orders.features.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface ClientRepository extends JpaRepository<Client, UUID> {
}
