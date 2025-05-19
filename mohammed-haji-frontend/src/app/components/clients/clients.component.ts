import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';

@Component({
  selector: 'app-clients',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clients.component.html',
  styleUrls: []
})
export class ClientsComponent implements OnInit {
  clients: Client[] = [];
  selectedClient: Client | null = null;
  newClient: Client = {
    nom: '',
    email: ''
  };
  searchKeyword: string = '';
  mode: 'list' | 'new' | 'edit' = 'list';

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.clientService.getClients().subscribe({
      next: (data) => {
        this.clients = data;
      },
      error: (err) => {
        console.error('Error loading clients:', err);
        // Here you might want to add error handling UI feedback
      }
    });
  }

  searchClients(): void {
    if (this.searchKeyword.trim()) {
      this.clientService.searchClients(this.searchKeyword).subscribe({
        next: (data) => {
          this.clients = data;
        },
        error: (err) => {
          console.error('Error searching clients:', err);
        }
      });
    } else {
      this.loadClients();
    }
  }

  saveClient(): void {
    if (this.mode === 'new') {
      this.clientService.createClient(this.newClient).subscribe({
        next: () => {
          this.loadClients();
          this.mode = 'list';
          this.resetForm();
        },
        error: (err) => {
          console.error('Error creating client:', err);
        }
      });
    } else if (this.mode === 'edit' && this.selectedClient) {
      this.clientService.updateClient(this.selectedClient.id!, this.selectedClient).subscribe({
        next: () => {
          this.loadClients();
          this.mode = 'list';
          this.selectedClient = null;
        },
        error: (err) => {
          console.error('Error updating client:', err);
        }
      });
    }
  }

  editClient(client: Client): void {
    this.selectedClient = { ...client };
    this.mode = 'edit';
  }

  deleteClient(id: number): void {
    if (confirm('Are you sure you want to delete this client?')) {
      this.clientService.deleteClient(id).subscribe({
        next: () => {
          this.loadClients();
        },
        error: (err) => {
          console.error('Error deleting client:', err);
        }
      });
    }
  }

  resetForm(): void {
    this.newClient = {
      nom: '',
      email: ''
    };
    this.selectedClient = null;
  }

  switchMode(mode: 'list' | 'new' | 'edit'): void {
    this.mode = mode;
    if (mode === 'new') {
      this.resetForm();
    }
  }
}
