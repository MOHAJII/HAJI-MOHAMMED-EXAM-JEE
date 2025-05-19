import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mx-5">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">Gestion des Crédits</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link" routerLink="/dashboard" routerLinkActive="active">
                <i class="bi bi-speedometer2"></i> Dashboard
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/clients" routerLinkActive="active">
                <i class="bi bi-people"></i> Clients
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/credits" routerLinkActive="active">
                <i class="bi bi-credit-card"></i> Crédits
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  `,
  styles: [`
    .navbar-nav .nav-link.active {
      color: #fff;
      background-color: rgba(255,255,255,0.1);
      border-radius: 4px;
    }
    .nav-link i {
      margin-right: 5px;
    }
  `]
})
export class NavbarComponent {}
