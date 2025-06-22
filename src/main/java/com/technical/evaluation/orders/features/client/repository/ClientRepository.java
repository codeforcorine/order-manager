package com.technical.evaluation.orders.features.client.repository;

import com.technical.evaluation.orders.features.client.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Page<Client> findAll(Pageable pageable);
}
