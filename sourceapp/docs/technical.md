## Popis aplikace a struktury
Aplikace je napsaná ve Spring Boot, využívá také Spring Security, Spring Validation, JPA a databázi H2.

### Struktura souborů
V rootu projektu lze najít soubor README.MD, který odkazuje na všechnu dokumentaci projektu.
Také je zde pom.xml, který obsahuje všechny závislosti aplikace.
Ve složce docs jsou uloženy všechny diagramy a dodatečné markdown soubory na dokumentaci.

Ve složce data jsou data uložená databází.
Ve složce src je kód projektu a testy.
Aplikace je dělená po vrstvách. Kdyby byla o krapet větší, už bychom zvážili rozdělit složky po jednotlivých features.

### Technický popis
Aplikace využívá databázi H2 přepnuté do persistnentního módu.
Inicializace dat v databázi (vytvoření rolí a admin uživatele) řeší command line runner DatabaseInitializer.
Aplikace využívá JPQL a JpaRepositories. Na výjimky používá třídu GlobalExceptionHandler v package exception.
Aplikace využívá constructor injection jako jedinou metodu DI.

Je to stavěné jako REST API. Autentizace je udělanéápřes HTTP BASIC a zabezpečení metod je děláno přes @PreAuthorize.
PreAutorizace je dělaná v servisní vrstvě, aby byla aplikace bezpečná i s jiným controllerem.
Validace je dělená především na servisní vrstvě, ale základní validace jsou i v DTO.


## Jak aplikaci spustit
Aplikace je stavěná jako klasický maven projekt,
tedy přes `mvn package` jde aplikaci zkompilovat a potom spustit jar soubor v target.
Veškeré závislosti a konfigurace jsou uvnitř projektu, není třeba dělat nic jiného než načíst pom.xml.

Až aplikace naběhne, lze z ní interagovat přes REST rozhraní na localhost:8080,
například pomocí připravených testcasů v [docs/testcases.md](testcases.md)

Pro inspiraci je dostupná swagger dokumentace na http://localhost:8080/swagger-ui.html

Důležité je zmínit, že při použití Postman je nutné použít možnost "No auth" při veřejných endpointech,
např. u registrace, ale "Basic Auth" s vyplněným jménem a heslem u všeho, co požaduje autentizaci. 
Instrukce jsou i v souboru s testcasy.

## Pocity a zkušenosti
Musím říct, že jsme velice rádi, že jsme se do tohoto projektu pustili a opravdu hodně nám to dalo.
Oceňujeme praktické zaměření předmětu a velice zajímavé přednášky i cvičení. Co nám ze cvičení nedošlo je to,
že v reálném projektu je třeba využívat exception handlerů, abychom nemuseli pořád kontrolovat výjimky, to jsme zjistili až na konzultaci.
Také bychom ocenili, kdyby v rámci jednoho souvislého úseku času bylo předvedeno jak sestavit feature od modelu až po DTO.
DTO jsou velice důležitá, ale nebyla na cvičení předvedena. 

Rozhodně největší problémy jsme měli s Postmanem a Spring Security. Nevěděli jsme, jaké možnosti zabezpečení máme,
(form, basic, jwt) a jakým způsobem je nakonfigurovat v spring security. Finální verze eshopu nám bohužel nešla spustit (myšleno s frontendem).
Vůbec nám nedošlo, že pokud máme v Postman zvolené basic auth a nevyplněné žádné údaje,
požadavek bude pořád vyhodnocován jako pořadavek s autentizací, místo požadavku bez, tedy nepřihlášený uživatel.
Aby se stalo to, musíme se přepnout na "No auth". Nakonec jsme ale zjistili, že pro naše potřeby stačí velmi jednoduchá konfigurace spring security,
která víceméně jen zapne basic auth a přenechá filtrování preAutorizačním anotacím.

Tato semestrálka nás naučila spoustu věcí a přijde nám jako jedna z nejužitečnějších co jsme zatím na fakultě měli. 
Ať už s se jedná o databázových znalosti JPA, ORM, JPQL, JpaRepositories,
nebo zabezpečení aplikací, validaci, RESTu a HTTP, a i testování, všechno velice praktické a zajímavé.
Jsme také velice rádi, že jsme se naučili takový obecný kontext a architekturu těchto aplikací a pevně věříme,
že tyto znalosti bychom dokázali jednoduše využít i kdybychom backend dělali v jiném jazyce nebo frameworku.

## Kde najít databázové techniky
Named Query a ordering v model/Loan

Cascade v model/Roles (kaskáda zde zabraňuje referenci neuložené transientní instance při inicializaci)

