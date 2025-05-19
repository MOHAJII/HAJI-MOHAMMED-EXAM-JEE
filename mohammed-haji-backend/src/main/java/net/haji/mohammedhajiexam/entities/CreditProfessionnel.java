package net.haji.mohammedhajiexam.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter @ToString @SuperBuilder
@AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("PROF")
public class CreditProfessionnel extends Credit {
    private String motif;
    private String raisonSociale;
}
