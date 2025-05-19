package net.haji.mohammedhajiexam.mappers;

import net.haji.mohammedhajiexam.dtos.ClientDTO;
import net.haji.mohammedhajiexam.dtos.CreditDTO;
import net.haji.mohammedhajiexam.dtos.RemboursementDTO;
import net.haji.mohammedhajiexam.entities.*;
import org.springframework.stereotype.Component;

@Component
public class CreditMappers {
    // Client Mappers
    public ClientDTO fromClient(Client client) {
        if (client == null) return null;
        return ClientDTO.builder()
                .id(client.getId())
                .nom(client.getNom())
                .email(client.getEmail())
                .build();
    }

    public Client toClient(ClientDTO clientDTO) {
        if (clientDTO == null) return null;
        return Client.builder()
                .id(clientDTO.getId())
                .nom(clientDTO.getNom())
                .email(clientDTO.getEmail())
                .build();
    }

    // Credit Mappers
    public CreditDTO fromCredit(Credit credit) {
        if (credit == null) return null;

        CreditDTO.CreditDTOBuilder builder = CreditDTO.builder()
                .id(credit.getId())
                .dateDemande(credit.getDateDemande())
                .statut(credit.getStatut())
                .dateAcceptation(credit.getDateAcceptation())
                .montant(credit.getMontant())
                .dureeRemboursement(credit.getDureeRemboursement())
                .tauxInteret(credit.getTauxInteret())
                .clientId(credit.getClient() != null ? credit.getClient().getId() : null);

        if (credit instanceof CreditImmobilier) {
            CreditImmobilier immobilier = (CreditImmobilier) credit;
            builder.discriminator("IMMO")
                   .typeBien(immobilier.getTypeBien());
        } else if (credit instanceof CreditPersonnel) {
            CreditPersonnel personnel = (CreditPersonnel) credit;
            builder.discriminator("PERS")
                   .motif(personnel.getMotif());
        } else if (credit instanceof CreditProfessionnel) {
            CreditProfessionnel professionnel = (CreditProfessionnel) credit;
            builder.discriminator("PROF")
                   .motif(professionnel.getMotif())
                   .raisonSociale(professionnel.getRaisonSociale());
        }

        return builder.build();
    }

    public Credit toCredit(CreditDTO creditDTO) {
        if (creditDTO == null) return null;

        switch (creditDTO.getDiscriminator()) {
            case "IMMO":
                return CreditImmobilier.builder()
                        .id(creditDTO.getId())
                        .dateDemande(creditDTO.getDateDemande())
                        .statut(creditDTO.getStatut())
                        .dateAcceptation(creditDTO.getDateAcceptation())
                        .montant(creditDTO.getMontant())
                        .dureeRemboursement(creditDTO.getDureeRemboursement())
                        .tauxInteret(creditDTO.getTauxInteret())
                        .typeBien(creditDTO.getTypeBien())
                        .build();

            case "PERS":
                return CreditPersonnel.builder()
                        .id(creditDTO.getId())
                        .dateDemande(creditDTO.getDateDemande())
                        .statut(creditDTO.getStatut())
                        .dateAcceptation(creditDTO.getDateAcceptation())
                        .montant(creditDTO.getMontant())
                        .dureeRemboursement(creditDTO.getDureeRemboursement())
                        .tauxInteret(creditDTO.getTauxInteret())
                        .motif(creditDTO.getMotif())
                        .build();

            case "PROF":
                return CreditProfessionnel.builder()
                        .id(creditDTO.getId())
                        .dateDemande(creditDTO.getDateDemande())
                        .statut(creditDTO.getStatut())
                        .dateAcceptation(creditDTO.getDateAcceptation())
                        .montant(creditDTO.getMontant())
                        .dureeRemboursement(creditDTO.getDureeRemboursement())
                        .tauxInteret(creditDTO.getTauxInteret())
                        .motif(creditDTO.getMotif())
                        .raisonSociale(creditDTO.getRaisonSociale())
                        .build();

            default:
                throw new IllegalArgumentException("Type de crédit inconnu: " + creditDTO.getDiscriminator());
        }
    }

    // Remboursement Mappers
    public RemboursementDTO fromRemboursement(Remboursement remboursement) {
        if (remboursement == null) return null;
        return RemboursementDTO.builder()
                .id(remboursement.getId())
                .date(remboursement.getDate())
                .montant(remboursement.getMontant())
                .type(remboursement.getType())
                .creditId(remboursement.getCredit() != null ? remboursement.getCredit().getId() : null)
                .build();
    }

    public Remboursement toRemboursement(RemboursementDTO remboursementDTO) {
        if (remboursementDTO == null) return null;
        return Remboursement.builder()
                .id(remboursementDTO.getId())
                .date(remboursementDTO.getDate())
                .montant(remboursementDTO.getMontant())
                .type(remboursementDTO.getType())
                .build();
    }
}
