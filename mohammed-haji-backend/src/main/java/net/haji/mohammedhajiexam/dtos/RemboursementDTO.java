package net.haji.mohammedhajiexam.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.haji.mohammedhajiexam.enums.TypeRemboursement;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemboursementDTO {
    private Long id;
    private Date date;
    private double montant;
    private TypeRemboursement type;
    private Long creditId;
}
