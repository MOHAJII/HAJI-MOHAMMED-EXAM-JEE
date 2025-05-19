package net.haji.mohammedhajiexam.repositories;

import net.haji.mohammedhajiexam.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Find client by email
    Client findByEmail(String email);
    // Find clients by name (partial match)
    List<Client> findByNomContainingIgnoreCase(String nom);
}