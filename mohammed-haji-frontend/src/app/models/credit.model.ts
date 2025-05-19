export enum StatutCredit {
    EN_COURS = 'EN_COURS',
    ACCEPTE = 'ACCEPTE',
    REJETE = 'REJETE'
}

export enum TypeBien {
    APPARTEMENT = 'APPARTEMENT'
    // Ajoutez d'autres types de biens si nécessaire
}

export interface Credit {
    id?: number;
    dateDemande?: string;
    statut?: StatutCredit;
    montant: number;
    dureeRemboursement: number;
    tauxInteret: number;
    clientId: number;
    discriminator: string;
    typeBien: TypeBien;
}

export interface CreditDecision {
    decision: StatutCredit;
}
