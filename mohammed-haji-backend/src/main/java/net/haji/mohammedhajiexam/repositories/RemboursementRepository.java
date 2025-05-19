package net.haji.mohammedhajiexam.repositories;

import net.haji.mohammedhajiexam.entities.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {
    // Find repayments by credit
    List<Remboursement> findByCreditId(Long creditId);
    // Find repayments by type
    List<Remboursement> findByType(String type);
}