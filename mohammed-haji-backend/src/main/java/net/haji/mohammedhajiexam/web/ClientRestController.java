package net.haji.mohammedhajiexam.web;

import lombok.AllArgsConstructor;
import net.haji.mohammedhajiexam.dtos.ClientDTO;
import net.haji.mohammedhajiexam.services.CreditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
@CrossOrigin("*")
public class ClientRestController {
    private CreditService creditService;

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return creditService.listClients();
    }

    @GetMapping("/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return creditService.getClient(id);
    }

    @PostMapping
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO) {
        return creditService.saveClient(clientDTO);
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        clientDTO.setId(id);
        return creditService.updateClient(clientDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        creditService.deleteClient(id);
    }

    @GetMapping("/search")
    public List<ClientDTO> searchClients(@RequestParam String keyword) {
        return creditService.searchClients(keyword);
    }
}
