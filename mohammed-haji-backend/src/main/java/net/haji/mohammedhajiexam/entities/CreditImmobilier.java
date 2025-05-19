package net.haji.mohammedhajiexam.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.haji.mohammedhajiexam.enums.TypeBien;

@Entity
@Getter @Setter @ToString @SuperBuilder
@AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("IMMO")
public class CreditImmobilier extends Credit {
    @Enumerated(EnumType.STRING)
    private TypeBien typeBien;
}
