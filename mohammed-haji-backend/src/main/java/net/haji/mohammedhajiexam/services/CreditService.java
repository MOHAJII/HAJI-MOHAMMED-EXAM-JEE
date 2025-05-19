package net.haji.mohammedhajiexam.services;

import net.haji.mohammedhajiexam.dtos.ClientDTO;
import net.haji.mohammedhajiexam.dtos.CreditDTO;
import net.haji.mohammedhajiexam.dtos.RemboursementDTO;
import net.haji.mohammedhajiexam.enums.StatutCredit;
import net.haji.mohammedhajiexam.enums.TypeRemboursement;

import java.util.List;

public interface CreditService {
    // Client operations
    ClientDTO saveClient(ClientDTO clientDTO);
    ClientDTO updateClient(ClientDTO clientDTO);
    List<ClientDTO> listClients();
    ClientDTO getClient(Long clientId);
    void deleteClient(Long clientId);
    List<ClientDTO> searchClients(String keyword);

    // Credit operations
    CreditDTO saveCredit(CreditDTO creditDTO);
    CreditDTO updateCredit(CreditDTO creditDTO);
    CreditDTO getCredit(Long creditId);
    List<CreditDTO> listCredits();
    List<CreditDTO> getCreditsByClient(Long clientId);
    List<CreditDTO> getCreditsByStatus(StatutCredit status);
    void deleteCredit(Long creditId);

    // Specialized Credit operations
    List<CreditDTO> getImmobilierCreditsByClient(Long clientId);
    List<CreditDTO> getPersonnelCreditsByClient(Long clientId);
    List<CreditDTO> getProfessionnelCreditsByClient(Long clientId);
    List<CreditDTO> searchCreditsByMotif(String motif);
    List<CreditDTO> searchCreditsByCompany(String raisonSociale);

    // Credit processing
    CreditDTO processCredit(Long creditId, StatutCredit decision);
    double calculateMensualite(Long creditId);
    double calculateTotalRestant(Long creditId);

    // Remboursement operations
    RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO);
    List<RemboursementDTO> getRemboursementsByCredit(Long creditId);
    List<RemboursementDTO> getRemboursementsByType(TypeRemboursement type);
    double getTotalRemboursements(Long creditId);

    // Statistics
    long countTotalCredits();
    long countCreditsByStatus(StatutCredit status);
    double calculateTotalCreditAmount();
    double calculateAverageInterestRate();
}
