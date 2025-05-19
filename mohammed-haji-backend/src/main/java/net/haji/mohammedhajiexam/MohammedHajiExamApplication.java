package net.haji.mohammedhajiexam;

import net.haji.mohammedhajiexam.entities.*;
import net.haji.mohammedhajiexam.enums.StatutCredit;
import net.haji.mohammedhajiexam.enums.TypeBien;
import net.haji.mohammedhajiexam.enums.TypeRemboursement;
import net.haji.mohammedhajiexam.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class MohammedHajiExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(MohammedHajiExamApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            ClientRepository clientRepository,
            CreditRepository creditRepository,
            CreditImmobilierRepository creditImmobilierRepository,
            CreditPersonnelRepository creditPersonnelRepository,
            CreditProfessionnelRepository creditProfessionnelRepository,
            RemboursementRepository remboursementRepository
    ) {
        return args -> {
            // Create clients
            Stream.of(
                    Client.builder().nom("Hassan Alami").email("hassan.alami@gmail.com").build(),
                    Client.builder().nom("Samira Radi").email("samira.radi@gmail.com").build(),
                    Client.builder().nom("Karim Bennani").email("karim.bennani@gmail.com").build()
            ).forEach(client -> {
                clientRepository.save(client);
            });

            Client client1 = clientRepository.findByEmail("hassan.alami@gmail.com");
            Client client2 = clientRepository.findByEmail("samira.radi@gmail.com");
            Client client3 = clientRepository.findByEmail("karim.bennani@gmail.com");

            // Create Credits Immobilier
            CreditImmobilier creditImmo1 = CreditImmobilier.builder()
                    .client(client1)
                    .dateDemande(new Date())
                    .dateAcceptation(new Date())
                    .montant(800000.0)
                    .dureeRemboursement(240) // 20 years
                    .tauxInteret(4.5)
                    .statut(StatutCredit.ACCEPTE)
                    .typeBien(TypeBien.APPARTEMENT)
                    .build();
            creditImmobilierRepository.save(creditImmo1);

            // Create Credit Personnel
            CreditPersonnel creditPerso = CreditPersonnel.builder()
                    .client(client2)
                    .dateDemande(new Date())
                    .dateAcceptation(new Date())
                    .montant(150000.0)
                    .dureeRemboursement(60) // 5 years
                    .tauxInteret(7.5)
                    .statut(StatutCredit.ACCEPTE)
                    .motif("Achat voiture")
                    .build();
            creditPersonnelRepository.save(creditPerso);

            // Create Credit Professionnel
            CreditProfessionnel creditPro = CreditProfessionnel.builder()
                    .client(client3)
                    .dateDemande(new Date())
                    .montant(500000.0)
                    .dureeRemboursement(84) // 7 years
                    .tauxInteret(6.0)
                    .statut(StatutCredit.EN_COURS)
                    .motif("Expansion entreprise")
                    .raisonSociale("Tech Solutions SARL")
                    .build();
            creditProfessionnelRepository.save(creditPro);

            // Create some repayments
            // For Immobilier credit
            Remboursement remb1 = Remboursement.builder()
                    .credit(creditImmo1)
                    .date(new Date())
                    .montant(4500.0)
                    .type(TypeRemboursement.MENSUALITE)
                    .build();
            remboursementRepository.save(remb1);

            // For Personal credit
            Remboursement remb2 = Remboursement.builder()
                    .credit(creditPerso)
                    .date(new Date())
                    .montant(3000.0)
                    .type(TypeRemboursement.MENSUALITE)
                    .build();
            remboursementRepository.save(remb2);

            // Add an early repayment
            Remboursement remb3 = Remboursement.builder()
                    .credit(creditPerso)
                    .date(new Date())
                    .montant(20000.0)
                    .type(TypeRemboursement.REMBOURSEMENT_ANTICIPE)
                    .build();
            remboursementRepository.save(remb3);
        };
    }
}
