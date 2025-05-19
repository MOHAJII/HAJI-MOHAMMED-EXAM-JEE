import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreditService } from '../../services/credit.service';
import { ClientService } from '../../services/client.service';
import { Credit } from '../../models/credit.model';
import { Client } from '../../models/client.model';

@Component({
  selector: 'app-credits',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './credits.component.html',
  styleUrls: []
})
export class CreditsComponent implements OnInit {
  credits: Credit[] = [];
  clients: Client[] = [];
  selectedCredit: Credit | null = null;
  newCredit: Credit = {
    montant: 0,
    dureeRemboursement: 0,
    tauxInteret: 0,
    clientId: 0,
    discriminator: 'IMMO',
    typeBien: 'APPARTEMENT'
  };
  selectedStatus: string = '';
  selectedClientId: number = 0;
  mode: 'list' | 'new' | 'edit' = 'list';
  mensualite: number = 0;

  constructor(
    private creditService: CreditService,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    this.loadCredits();
    this.loadClients();
  }

  loadCredits(): void {
    if (this.selectedStatus) {
      this.loadCreditsByStatus(this.selectedStatus);
    } else if (this.selectedClientId) {
      this.loadCreditsByClient(this.selectedClientId);
    } else {
      this.creditService.getCredits().subscribe({
        next: (data) => {
          this.credits = data;
        },
        error: (err) => {
          console.error('Error loading credits:', err);
        }
      });
    }
  }

  loadClients(): void {
    this.clientService.getClients().subscribe({
      next: (data) => {
        this.clients = data;
      },
      error: (err) => {
        console.error('Error loading clients:', err);
      }
    });
  }

  loadCreditsByStatus(status: string): void {
    this.creditService.getCreditsByStatus(status).subscribe({
      next: (data) => {
        this.credits = data;
      },
      error: (err) => {
        console.error('Error loading credits by status:', err);
      }
    });
  }

  loadCreditsByClient(clientId: number): void {
    this.creditService.getCreditsByClient(clientId).subscribe({
      next: (data) => {
        this.credits = data;
      },
      error: (err) => {
        console.error('Error loading credits by client:', err);
      }
    });
  }

  saveCredit(): void {
    this.creditService.createCredit(this.newCredit).subscribe({
      next: () => {
        this.loadCredits();
        this.mode = 'list';
        this.resetForm();
      },
      error: (err) => {
        console.error('Error creating credit:', err);
      }
    });
  }

  processCredit(creditId: number, decision: 'ACCEPTE' | 'REJETE'): void {
    this.creditService.processCredit(creditId, { decision }).subscribe({
      next: () => {
        this.loadCredits();
      },
      error: (err) => {
        console.error('Error processing credit:', err);
      }
    });
  }

  calculateMensualite(creditId: number): void {
    this.creditService.calculateMensualite(creditId).subscribe({
      next: (amount) => {
        this.mensualite = amount;
      },
      error: (err) => {
        console.error('Error calculating mensualite:', err);
      }
    });
  }

  resetForm(): void {
    this.newCredit = {
      montant: 0,
      dureeRemboursement: 0,
      tauxInteret: 0,
      clientId: 0,
      discriminator: 'IMMO',
      typeBien: 'APPARTEMENT'
    };
  }

  switchMode(mode: 'list' | 'new' | 'edit'): void {
    this.mode = mode;
    if (mode === 'new') {
      this.resetForm();
    }
  }
}
