package net.haji.mohammedhajiexam.repositories;

import net.haji.mohammedhajiexam.entities.CreditImmobilier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditImmobilierRepository extends JpaRepository<CreditImmobilier, Long> {
    // Find immobilier credits by client
    List<CreditImmobilier> findByClientId(Long clientId);
    // Find immobilier credits by property type
    List<CreditImmobilier> findByTypeBien(String typeBien);
}