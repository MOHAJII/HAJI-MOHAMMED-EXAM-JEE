import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Credit, CreditDecision } from '../models/credit.model';

@Injectable({
  providedIn: 'root'
})
export class CreditService {
  private baseUrl = 'http://localhost:8088';
  private apiUrl = `${this.baseUrl}/api/credits`;

  constructor(private http: HttpClient) { }

  // Récupérer tous les crédits
  getCredits(): Observable<Credit[]> {
    return this.http.get<Credit[]>(this.apiUrl);
  }

  // Créer un nouveau crédit
  createCredit(credit: Credit): Observable<Credit> {
    return this.http.post<Credit>(this.apiUrl, credit);
  }

  // Obtenir les crédits par client
  getCreditsByClient(clientId: number): Observable<Credit[]> {
    return this.http.get<Credit[]>(`${this.apiUrl}/client/${clientId}`);
  }

  // Obtenir les crédits par statut
  getCreditsByStatus(status: string): Observable<Credit[]> {
    return this.http.get<Credit[]>(`${this.apiUrl}/status/${status}`);
  }

  // Traiter un crédit (accepter/rejeter)
  processCredit(id: number, decision: CreditDecision): Observable<Credit> {
    return this.http.put<Credit>(`${this.apiUrl}/${id}/process`, decision);
  }

  // Calculer la mensualité
  calculateMensualite(id: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/${id}/mensualite`);
  }

  // Statistiques
  getTotalCredits(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/stats/count`);
  }

  getTotalAmount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/stats/total-amount`);
  }

  getAverageRate(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/stats/average-rate`);
  }
}
