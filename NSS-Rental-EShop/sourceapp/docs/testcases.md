## Testovací scénář

**Upozornění**: Endpointy dostupné pro nepřihlášené uživatele je v Postman nutné využívat s "no auth",
endpointy které vyžadují přihlášení používejte s "Basic auth" s username a password.
Normální uživatel jde zaregistrovat na /register, admin je předem vytvořený (jméno a heslo admin).
Server defaultně jede na localhost:8080

Karta Authorization, Type basic auth
Username: admin, Password: admin

## Scénař:

### Vložení kategorie
POST /categories/

    {
    "name": "sekacky"
    }

### Vložení produktu
POST /products/

    {
    "name": "Sekacka Pepa",
    "active": true,
    "dailyPrice": 403.2,
    "description": "seka travu",
    "categories": ["sekacky"],
    "manufacturer": "Lawnmeowers inc."
    }

### Seznam produktů podle kategorie
GET /products/categories/sekacky

### Změna produktu
PUT /products/

    {
    "oldName": "Sekacka Pepa",
    "newName": "Sekacka Josef",
    "active": true,
    "dailyPrice": 390,
    "description": "seka travu, nyni levneji"
    }

### Detail produktu
GET /products/Sekacka Josef

### Naskladnění produktu
POST /storage/

    {
    "serialNumber": "ASD293EAGH3",
    "productName": "Sekacka Josef"
    }

### Registrace zákazníka
"No auth"
POST /register

    {
    "username": "uzivatel",
    "password": "TEST1TEST",
    "email": "test@asd.asd",
    "fullName": "Franta Vomacka",
    "address": "Technicka 2",
    "phoneNumber": "+420 876 543 210"
    }


Karta Authorization, Type basic auth
Username: uzivatel
Password: TEST1TEST

### Vytvoření vypůjčky

POST /loans/ 

    {
    "fromDate": "2025-04-04",
    "toDate": "2025-05-05",
    "productNames": ["Sekacka Josef"]
    }


### Zobrazení všech výpůjček od přihlášeného uživatele
GET /loans/

### Vytvoření recenze
POST /reviews/

    {
    "productName": "Sekacka Josef",
    "text": "seká trávu hihi",
    "rating": 4
    }


### Vypsání všech recenzí produktu
GET /reviews/Sekacka Josef


Tady zase Basic auth
Username: admin, Password: admin
### Vrácení zboží na sklad
PUT /storage/return

    {
    "loanID": 1
    }
popřípadě jiné ID, které se ukázalo na minulé obrazovce GET /loans/

### Výpis výpůjček všech uživatelů (může jen admin)
GET /loans/all

Zde je vidět, že je vrácená, nicméně ne smazená, z účetních důvodů.