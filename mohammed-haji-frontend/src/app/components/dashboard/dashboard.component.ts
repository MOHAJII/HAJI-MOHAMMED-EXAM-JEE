import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreditService } from '../../services/credit.service';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';
import { Credit, StatutCredit } from '../../models/credit.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: []
})
export class DashboardComponent implements OnInit {
  totalCredits: number = 0;
  totalAmount: number = 0;
  averageRate: number = 0;
  recentClients: Client[] = [];
  recentCredits: Credit[] = [];
  creditsByStatus = {
    [StatutCredit.EN_COURS]: 0,
    [StatutCredit.ACCEPTE]: 0,
    [StatutCredit.REJETE]: 0
  };

  constructor(
    private creditService: CreditService,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  private loadDashboardData(): void {
    // Charger les statistiques des crédits
    this.creditService.getTotalCredits().subscribe(
      total => this.totalCredits = total
    );

    this.creditService.getTotalAmount().subscribe(
      amount => this.totalAmount = amount
    );

    this.creditService.getAverageRate().subscribe(
      rate => this.averageRate = rate
    );

    // Charger les derniers clients
    this.clientService.getClients().subscribe(
      clients => this.recentClients = clients.slice(0, 5)
    );

    // Charger les derniers crédits
    this.creditService.getCredits().subscribe(credits => {
      this.recentCredits = credits.slice(0, 5);

      // Calculer le nombre de crédits par statut
      credits.forEach(credit => {
        if (credit.statut) {
          this.creditsByStatus[credit.statut]++;
        }
      });
    });
  }
}
