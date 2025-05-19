package net.haji.mohammedhajiexam.repositories;

import net.haji.mohammedhajiexam.entities.CreditPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditPersonnelRepository extends JpaRepository<CreditPersonnel, Long> {
    // Find personal credits by client
    List<CreditPersonnel> findByClientId(Long clientId);
    // Find personal credits by purpose (partial match)
    List<CreditPersonnel> findByMotifContainingIgnoreCase(String motif);
}