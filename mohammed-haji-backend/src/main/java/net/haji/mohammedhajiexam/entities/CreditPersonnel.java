package net.haji.mohammedhajiexam.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter @ToString @SuperBuilder
@AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("PERS")
public class CreditPersonnel extends Credit {
    private String motif; // e.g., achat de voiture, etudes, travaux
}
