package net.haji.mohammedhajiexam.web;

import lombok.AllArgsConstructor;
import net.haji.mohammedhajiexam.dtos.CreditDTO;
import net.haji.mohammedhajiexam.dtos.RemboursementDTO;
import net.haji.mohammedhajiexam.enums.StatutCredit;
import net.haji.mohammedhajiexam.enums.TypeRemboursement;
import net.haji.mohammedhajiexam.services.CreditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin("*")
public class CreditRestController {
    private CreditService creditService;

    // Credit operations
    @GetMapping("/credits")
    public List<CreditDTO> getAllCredits() {
        return creditService.listCredits();
    }

    @GetMapping("/credits/{id}")
    public CreditDTO getCredit(@PathVariable Long id) {
        return creditService.getCredit(id);
    }

    @PostMapping("/credits")
    public CreditDTO saveCredit(@RequestBody CreditDTO creditDTO) {
        return creditService.saveCredit(creditDTO);
    }

    @PutMapping("/credits/{id}")
    public CreditDTO updateCredit(@PathVariable Long id, @RequestBody CreditDTO creditDTO) {
        creditDTO.setId(id);
        return creditService.updateCredit(creditDTO);
    }

    @DeleteMapping("/credits/{id}")
    public void deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
    }

    // Specialized credit operations
    @GetMapping("/credits/client/{clientId}")
    public List<CreditDTO> getCreditsByClient(@PathVariable Long clientId) {
        return creditService.getCreditsByClient(clientId);
    }

    @GetMapping("/credits/status/{status}")
    public List<CreditDTO> getCreditsByStatus(@PathVariable StatutCredit status) {
        return creditService.getCreditsByStatus(status);
    }

    @GetMapping("/credits/immobilier/client/{clientId}")
    public List<CreditDTO> getImmobilierCredits(@PathVariable Long clientId) {
        return creditService.getImmobilierCreditsByClient(clientId);
    }

    @GetMapping("/credits/personnel/client/{clientId}")
    public List<CreditDTO> getPersonnelCredits(@PathVariable Long clientId) {
        return creditService.getPersonnelCreditsByClient(clientId);
    }

    @GetMapping("/credits/professionnel/client/{clientId}")
    public List<CreditDTO> getProfessionnelCredits(@PathVariable Long clientId) {
        return creditService.getProfessionnelCreditsByClient(clientId);
    }

    @GetMapping("/credits/search/motif")
    public List<CreditDTO> searchByMotif(@RequestParam String motif) {
        return creditService.searchCreditsByMotif(motif);
    }

    @GetMapping("/credits/search/company")
    public List<CreditDTO> searchByCompany(@RequestParam String raisonSociale) {
        return creditService.searchCreditsByCompany(raisonSociale);
    }

    // Credit processing
    @PutMapping("/credits/{id}/process")
    public CreditDTO processCredit(@PathVariable Long id, @RequestBody Map<String, StatutCredit> decision) {
        return creditService.processCredit(id, decision.get("decision"));
    }

    @GetMapping("/credits/{id}/mensualite")
    public double calculateMensualite(@PathVariable Long id) {
        return creditService.calculateMensualite(id);
    }

    @GetMapping("/credits/{id}/restant")
    public double calculateRestant(@PathVariable Long id) {
        return creditService.calculateTotalRestant(id);
    }

    // Remboursement operations
    @PostMapping("/remboursements")
    public RemboursementDTO saveRemboursement(@RequestBody RemboursementDTO remboursementDTO) {
        return creditService.saveRemboursement(remboursementDTO);
    }

    @GetMapping("/remboursements/credit/{creditId}")
    public List<RemboursementDTO> getRemboursementsByCredit(@PathVariable Long creditId) {
        return creditService.getRemboursementsByCredit(creditId);
    }

    @GetMapping("/remboursements/type/{type}")
    public List<RemboursementDTO> getRemboursementsByType(@PathVariable TypeRemboursement type) {
        return creditService.getRemboursementsByType(type);
    }

    @GetMapping("/remboursements/credit/{creditId}/total")
    public double getTotalRemboursements(@PathVariable Long creditId) {
        return creditService.getTotalRemboursements(creditId);
    }

    // Statistics endpoints
    @GetMapping("/credits/stats/count")
    public long getTotalCreditsCount() {
        return creditService.countTotalCredits();
    }

    @GetMapping("/credits/stats/count/status/{status}")
    public long getCreditCountByStatus(@PathVariable StatutCredit status) {
        return creditService.countCreditsByStatus(status);
    }

    @GetMapping("/credits/stats/total-amount")
    public double getTotalCreditAmount() {
        return creditService.calculateTotalCreditAmount();
    }

    @GetMapping("/credits/stats/average-rate")
    public double getAverageInterestRate() {
        return creditService.calculateAverageInterestRate();
    }
}
