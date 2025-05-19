package net.haji.mohammedhajiexam.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.haji.mohammedhajiexam.enums.StatutCredit;
import net.haji.mohammedhajiexam.enums.TypeBien;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditDTO {
    private Long id;
    private Date dateDemande;
    private StatutCredit statut;
    private Date dateAcceptation;
    private double montant;
    private int dureeRemboursement;
    private double tauxInteret;
    private Long clientId;
    private String discriminator;  // Pour identifier le type de crédit (IMMO, PERS, PROF)

    // Champs spécifiques au CreditImmobilier
    private TypeBien typeBien;

    // Champs spécifiques au CreditPersonnel et CreditProfessionnel
    private String motif;

    // Champ spécifique au CreditProfessionnel
    private String raisonSociale;
}
