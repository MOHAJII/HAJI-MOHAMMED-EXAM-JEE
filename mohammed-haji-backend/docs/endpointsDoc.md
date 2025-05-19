# Documentation des Endpoints de l'API de Gestion des Crédits

## Gestion des Clients

### 1. Récupérer tous les clients
- **URL** : `/api/clients`
- **Méthode** : GET
- **Description** : Retourne la liste de tous les clients
- **Réponse** : Liste de `ClientDTO`
```json
[
    {
        "id": 1,
        "nom": "Hassan Alami",
        "email": "hassan.alami@gmail.com"
    }
]
```

### 2. Récupérer un client spécifique
- **URL** : `/api/clients/{id}`
- **Méthode** : GET
- **Description** : Retourne les détails d'un client
- **Paramètres** : `id` (Long) - ID du client
- **Réponse** : `ClientDTO`

### 3. Créer un nouveau client
- **URL** : `/api/clients`
- **Méthode** : POST
- **Description** : Crée un nouveau client
- **Corps de la requête** : `ClientDTO`
```json
{
    "nom": "Nouveau Client",
    "email": "email@example.com"
}
```
- **Réponse** : `ClientDTO` créé avec ID

### 4. Mettre à jour un client
- **URL** : `/api/clients/{id}`
- **Méthode** : PUT
- **Description** : Met à jour les informations d'un client
- **Paramètres** : `id` (Long) - ID du client
- **Corps de la requête** : `ClientDTO`

### 5. Supprimer un client
- **URL** : `/api/clients/{id}`
- **Méthode** : DELETE
- **Description** : Supprime un client
- **Paramètres** : `id` (Long) - ID du client

### 6. Rechercher des clients
- **URL** : `/api/clients/search?keyword=value`
- **Méthode** : GET
- **Description** : Recherche des clients par nom
- **Paramètres** : `keyword` (String) - Mot-clé de recherche
- **Réponse** : Liste de `ClientDTO`

## Gestion des Crédits

### 1. Récupérer tous les crédits
- **URL** : `/api/credits`
- **Méthode** : GET
- **Description** : Retourne la liste de tous les crédits
- **Réponse** : Liste de `CreditDTO`
```json
[
    {
        "id": 1,
        "dateDemande": "2024-05-19",
        "statut": "EN_COURS",
        "montant": 800000.0,
        "dureeRemboursement": 240,
        "tauxInteret": 4.5,
        "clientId": 1,
        "discriminator": "IMMO",
        "typeBien": "APPARTEMENT"
    }
]
```

### 2. Créer un nouveau crédit
- **URL** : `/api/credits`
- **Méthode** : POST
- **Description** : Crée un nouveau crédit
- **Corps de la requête** : `CreditDTO`
```json
{
    "montant": 500000.0,
    "dureeRemboursement": 120,
    "tauxInteret": 4.5,
    "clientId": 1,
    "discriminator": "IMMO",
    "typeBien": "APPARTEMENT"
}
```

### 3. Obtenir les crédits par client
- **URL** : `/api/credits/client/{clientId}`
- **Méthode** : GET
- **Description** : Retourne tous les crédits d'un client
- **Paramètres** : `clientId` (Long)
- **Réponse** : Liste de `CreditDTO`

### 4. Obtenir les crédits par statut
- **URL** : `/api/credits/status/{status}`
- **Méthode** : GET
- **Description** : Retourne les crédits selon leur statut
- **Paramètres** : `status` (StatutCredit: EN_COURS, ACCEPTE, REJETE)
- **Réponse** : Liste de `CreditDTO`

### 5. Traiter un crédit
- **URL** : `/api/credits/{id}/process`
- **Méthode** : PUT
- **Description** : Traite une demande de crédit (acceptation/rejet)
- **Corps de la requête** :
```json
{
    "decision": "ACCEPTE"
}
```

### 6. Calculer la mensualité
- **URL** : `/api/credits/{id}/mensualite`
- **Méthode** : GET
- **Description** : Calcule la mensualité d'un crédit
- **Réponse** : double (montant mensuel)

## Gestion des Remboursements

### 1. Créer un remboursement
- **URL** : `/api/remboursements`
- **Méthode** : POST
- **Description** : Enregistre un nouveau remboursement
- **Corps de la requête** : `RemboursementDTO`
```json
{
    "date": "2024-05-19",
    "montant": 5000.0,
    "type": "MENSUALITE",
    "creditId": 1
}
```

### 2. Obtenir les remboursements d'un crédit
- **URL** : `/api/remboursements/credit/{creditId}`
- **Méthode** : GET
- **Description** : Retourne tous les remboursements d'un crédit
- **Paramètres** : `creditId` (Long)
- **Réponse** : Liste de `RemboursementDTO`

## Statistiques

### 1. Nombre total de crédits
- **URL** : `/api/credits/stats/count`
- **Méthode** : GET
- **Description** : Retourne le nombre total de crédits
- **Réponse** : long

### 2. Montant total des crédits
- **URL** : `/api/credits/stats/total-amount`
- **Méthode** : GET
- **Description** : Retourne le montant total des crédits
- **Réponse** : double

### 3. Taux d'intérêt moyen
- **URL** : `/api/credits/stats/average-rate`
- **Méthode** : GET
- **Description** : Retourne le taux d'intérêt moyen de tous les crédits
- **Réponse** : double

## Notes importantes

- Tous les endpoints sont accessibles avec le préfixe `/api`
- L'API utilise CORS et accepte les requêtes de toutes les origines (`@CrossOrigin("*")`)
- Les dates sont au format ISO-8601
- Les montants sont en dirhams (DH)
- Les durées de remboursement sont en mois
- Les taux d'intérêt sont en pourcentage
