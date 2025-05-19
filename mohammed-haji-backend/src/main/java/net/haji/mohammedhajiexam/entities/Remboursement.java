package net.haji.mohammedhajiexam.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import net.haji.mohammedhajiexam.enums.TypeRemboursement;

import java.util.Date;

@Entity
@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class Remboursement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private double montant;

    @Enumerated(EnumType.STRING)
    private TypeRemboursement type;

    @ManyToOne
    private Credit credit;
}
