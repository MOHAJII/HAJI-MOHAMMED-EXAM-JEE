package net.haji.mohammedhajiexam.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.haji.mohammedhajiexam.enums.StatutCredit;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @ToString @SuperBuilder
@AllArgsConstructor @NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_CREDIT", length = 4)
public abstract class Credit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateDemande;

    @Enumerated(EnumType.STRING)
    private StatutCredit statut;

    private Date dateAcceptation;
    private double montant;
    private int dureeRemboursement; // in months
    private double tauxInteret;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "credit")
    private List<Remboursement> remboursements;
}