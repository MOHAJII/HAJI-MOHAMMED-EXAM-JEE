package net.haji.mohammedhajiexam.services;

import lombok.AllArgsConstructor;
import net.haji.mohammedhajiexam.dtos.ClientDTO;
import net.haji.mohammedhajiexam.dtos.CreditDTO;
import net.haji.mohammedhajiexam.dtos.RemboursementDTO;
import net.haji.mohammedhajiexam.entities.*;
import net.haji.mohammedhajiexam.enums.StatutCredit;
import net.haji.mohammedhajiexam.enums.TypeRemboursement;
import net.haji.mohammedhajiexam.mappers.CreditMappers;
import net.haji.mohammedhajiexam.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final CreditImmobilierRepository creditImmobilierRepository;
    private final CreditPersonnelRepository creditPersonnelRepository;
    private final CreditProfessionnelRepository creditProfessionnelRepository;
    private final RemboursementRepository remboursementRepository;
    private final CreditMappers creditMappers;

    // Client operations
    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client client = creditMappers.toClient(clientDTO);
        Client savedClient = clientRepository.save(client);
        return creditMappers.fromClient(savedClient);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        Client client = clientRepository.findById(clientDTO.getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        client.setNom(clientDTO.getNom());
        client.setEmail(clientDTO.getEmail());
        Client updatedClient = clientRepository.save(client);
        return creditMappers.fromClient(updatedClient);
    }

    @Override
    public List<ClientDTO> listClients() {
        return clientRepository.findAll()
                .stream()
                .map(creditMappers::fromClient)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return creditMappers.fromClient(client);
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    public List<ClientDTO> searchClients(String keyword) {
        return clientRepository.findByNomContainingIgnoreCase(keyword)
                .stream()
                .map(creditMappers::fromClient)
                .collect(Collectors.toList());
    }

    // Credit operations
    @Override
    public CreditDTO saveCredit(CreditDTO creditDTO) {
        Credit credit = creditMappers.toCredit(creditDTO);
        Client client = clientRepository.findById(creditDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        credit.setClient(client);
        Credit savedCredit = creditRepository.save(credit);
        return creditMappers.fromCredit(savedCredit);
    }

    @Override
    public CreditDTO updateCredit(CreditDTO creditDTO) {
        Credit credit = creditRepository.findById(creditDTO.getId())
                .orElseThrow(() -> new RuntimeException("Credit not found"));
        Credit updatedCredit = creditMappers.toCredit(creditDTO);
        updatedCredit.setClient(credit.getClient());
        updatedCredit = creditRepository.save(updatedCredit);
        return creditMappers.fromCredit(updatedCredit);
    }

    @Override
    public CreditDTO getCredit(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found"));
        return creditMappers.fromCredit(credit);
    }

    @Override
    public List<CreditDTO> listCredits() {
        return creditRepository.findAll()
                .stream()
                .map(creditMappers::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getCreditsByClient(Long clientId) {
        return creditRepository.findByClientId(clientId)
                .stream()
                .map(creditMappers::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getCreditsByStatus(StatutCredit status) {
        return creditRepository.findByStatut(status)
                .stream()
                .map(creditMappers::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCredit(Long creditId) {
        creditRepository.deleteById(creditId);
    }

    // Specialized Credit operations
    @Override
    public List<CreditDTO> getImmobilierCreditsByClient(Long clientId) {
        return creditImmobilierRepository.findByClientId(clientId)
                .stream()
                .map(creditMappers::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getPersonnelCreditsByClient(Long clientId) {
        return creditPersonnelRepository.findByClientId(clientId)
                .stream()
                .map(creditMappers::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getProfessionnelCreditsByClient(Long clientId) {
        return creditProfessionnelRepository.findByClientId(clientId)
                .stream()
                .map(creditMappers::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> searchCreditsByMotif(String motif) {
        return creditPersonnelRepository.findByMotifContainingIgnoreCase(motif)
                .stream()
                .map(creditMappers::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> searchCreditsByCompany(String raisonSociale) {
        return creditProfessionnelRepository.findByRaisonSocialeContainingIgnoreCase(raisonSociale)
                .stream()
                .map(creditMappers::fromCredit)
                .collect(Collectors.toList());
    }

    // Credit processing
    @Override
    public CreditDTO processCredit(Long creditId, StatutCredit decision) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found"));
        credit.setStatut(decision);
        if (decision == StatutCredit.ACCEPTE) {
            credit.setDateAcceptation(new java.util.Date());
        }
        Credit updatedCredit = creditRepository.save(credit);
        return creditMappers.fromCredit(updatedCredit);
    }

    @Override
    public double calculateMensualite(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found"));
        double taux = credit.getTauxInteret() / 12 / 100; // Taux mensuel
        int nombreMois = credit.getDureeRemboursement();
        double montant = credit.getMontant();

        return (montant * taux * Math.pow(1 + taux, nombreMois)) /
               (Math.pow(1 + taux, nombreMois) - 1);
    }

    @Override
    public double calculateTotalRestant(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found"));
        double totalPaye = getTotalRemboursements(creditId);
        return credit.getMontant() - totalPaye;
    }

    // Remboursement operations
    @Override
    public RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO) {
        Remboursement remboursement = creditMappers.toRemboursement(remboursementDTO);
        Credit credit = creditRepository.findById(remboursementDTO.getCreditId())
                .orElseThrow(() -> new RuntimeException("Credit not found"));
        remboursement.setCredit(credit);
        Remboursement savedRemboursement = remboursementRepository.save(remboursement);
        return creditMappers.fromRemboursement(savedRemboursement);
    }

    @Override
    public List<RemboursementDTO> getRemboursementsByCredit(Long creditId) {
        return remboursementRepository.findByCreditId(creditId)
                .stream()
                .map(creditMappers::fromRemboursement)
                .collect(Collectors.toList());
    }

    @Override
    public List<RemboursementDTO> getRemboursementsByType(TypeRemboursement type) {
        return remboursementRepository.findByType(type.toString())
                .stream()
                .map(creditMappers::fromRemboursement)
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalRemboursements(Long creditId) {
        return remboursementRepository.findByCreditId(creditId)
                .stream()
                .mapToDouble(Remboursement::getMontant)
                .sum();
    }

    // Statistics
    @Override
    public long countTotalCredits() {
        return creditRepository.count();
    }

    @Override
    public long countCreditsByStatus(StatutCredit status) {
        return creditRepository.findByStatut(status).size();
    }

    @Override
    public double calculateTotalCreditAmount() {
        return creditRepository.findAll()
                .stream()
                .mapToDouble(Credit::getMontant)
                .sum();
    }

    @Override
    public double calculateAverageInterestRate() {
        List<Credit> credits = creditRepository.findAll();
        if (credits.isEmpty()) return 0;
        return credits.stream()
                .mapToDouble(Credit::getTauxInteret)
                .average()
                .orElse(0);
    }
}
