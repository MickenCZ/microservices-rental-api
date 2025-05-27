# Pravidla pro práci s Gitem v projektu Půjčovny

Tento dokument obsahuje základní pravidla pro práci s Gitem v našem projektu. Dodržování těchto pravidel pomůže udržet přehledný repozitář a usnadnit spolupráci.

## Strategie větvení

Používáme následující hlavní větve:

- **main** - Produkční kód připravený k nasazení
- **develop** - Integrační větev pro vývoj funkcí
- **feature branches** - Pro nové funkce
- **bugfix branches** - Pro opravy chyb
- **hotfix branches** - Pro kritické opravy v produkci

### Základní postup

1. Vývoj probíhá ve feature větvích vytvořených z větve `develop`
2. Dokončené funkce se slučují zpět do `develop` pomocí merge requestů
3. Když je kód připraven k vydání, větev `develop` se sloučí do `main`

## Pojmenování větví

Větve pojmenováváme v angličtině podle vzoru:
```
<typ>/<stručný-popis>
```

Kde:
- `<typ>` je jeden z: `feature`, `bugfix`, `hotfix`, `release`
- `<stručný-popis>` je krátký popis s pomlčkami místo mezer

Příklady:
```
feature/add-product-review
bugfix/validation-data-fix
hotfix/security-fix
```

## Pravidla pro commity

### Formát commit zprávy

Commit zprávy by měly být stručné a výstižné:
```
<typ>: <popis>
```

Kde:
- `<typ>` je jeden z: `feature`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
- `<popis>` je stručné vysvětlení změny v přítomném čase v angličtině

Příklady:
```
feature: let logged-in user to add product review
fix: corrected data validation in loan form
```

### Tipy pro commity

- Commity by měly být logicky ucelené (jedna změna = jeden commit)
- Pište jasné a popisné commit zprávy
- Dělejte commity často, ale ne příliš malé/bezvýznamné
- Před plánovaným mergem většího feature upozorněte tým na Messengeru (ideálně den předem)

## Merge requesty a kontrola kódu

1. Když je vaše funkce připravená, vytvořte merge request v GitLabu
2. Přiřaďte alespoň jednoho (jiného) člena týmu ke kontrole kódu
3. Po schválení a opravení případných připomínek můžete sloučit změny

### Priorita mergů

- Bugfixy mají přednost před feature branchemi
- Hotfixy mají absolutní přednost a mohou být sloučeny kdykoliv

## Řešení konfliktů

Pokud dojde ke konfliktům při slučování větví:

1. Aktualizujte svou větev podle develop větve:
   ```
   git checkout develop
   git pull
   git checkout vase-vetev
   git rebase develop
   ```

2. Vyřešte konflikty v souborech a dokončete rebase:
   ```
   git add opravene-soubory
   git rebase --continue
   ```

## Řešení kritických situací

### Oprava špatného commitu
Pro opravu posledního commitu (pouze pokud nebyl pushnut):
```
git commit --amend
```

### Vrácení změn z commitu
Pro vytvoření nového commitu, který vrátí předchozí změny:
```
git revert <commit-hash>
```

---