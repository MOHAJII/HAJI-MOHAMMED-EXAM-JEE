package net.haji.mohammedhajiexam.repositories;

import net.haji.mohammedhajiexam.entities.Credit;
import net.haji.mohammedhajiexam.enums.StatutCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    // Find credits by client
    List<Credit> findByClientId(Long clientId);
    // Find credits by status
    List<Credit> findByStatut(StatutCredit statut);
}