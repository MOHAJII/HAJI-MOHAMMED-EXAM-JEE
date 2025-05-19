package net.haji.mohammedhajiexam.repositories;

import net.haji.mohammedhajiexam.entities.CreditProfessionnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditProfessionnelRepository extends JpaRepository<CreditProfessionnel, Long> {
    // Find professional credits by client
    List<CreditProfessionnel> findByClientId(Long clientId);
    // Find professional credits by company name (partial match)
    List<CreditProfessionnel> findByRaisonSocialeContainingIgnoreCase(String raisonSociale);
}