# Zadanie nr 3. Software Engineer

## Spis treści
* [Opis i treść zadania](#opis-i-treść-zadania)
* [Użyte technologie](#użyte-technologie)
* [Instrukcja uruchomienia](#instrukcja-uruchomienia)
* [Instrukcja użytkowania](#instrukcja-użytkowania)
     * [Listowanie repozytoriów](#listowanie-repozytoriów)
     * [Zwracanie sumy gwiazdek](#zwracanie-sumy-gwiazdek)
* [Uwagi](#uwagi)
* [Propozycje na rozszerzenie projektu](#propozycje-na-rozszerzenie-projektu)

## Opis i treść zadania
Aplikacja została napisana w ramach procesu rekrutacyjnego do programu Summer e-Xperience 2021 Allegro.

Treść zadania to:
```
Stwórz oprogramowanie pozwalające na:
- listowanie repozytoriów (nazwa i liczba gwiazdek),
- zwracanie sumy gwiazdek we wszystkich repozytoriach,
dla dowolnego użytkownika serwisu GitHub.
Dane powinny być zwracane za pomocą protokołu HTTP.
```

## Użyte technologie
- Java 11
- Spring Boot 2.4.5
- WebFlux 
- Lombok 1.18.20

## Instrukcja uruchomienia
 W pierwszej kolejności należy pobrać repozytorium 

```cmd
git clone https://github.com/xNakero/github-repository-analyser.git
 ```

Aplikacja może zostać otworzona oraz uruchomiona w środowisku IntelliJ poprzez otwarcie pobranego repozytorium w programie oraz uruchomienie przy użyciu klasy *GithubRepositoryAnalyserApplication*.

Innym sposobem na uruchomienie aplikacji jest uruchomienie jej w terminalu. Do tego wymagany jest [maven](https://maven.apache.org/install.html).

W celu uruchomienia aplikacji należy w terminalu przejść do folderu z pobranym repozytorium
```cmd
cd github-repository-analyser
```
Następnie należy uruchomić aplikację przy użyciu

```
mvn spring-boot:run
```

## Instrukcja użytkowania
W celu użycia aplikacji należy użyć dowolnego klienta HTTP. Wszystkie poniższe endpointy wykonywane są przy użyciu metody GET.

### Listowanie repozytoriów
```
http://localhost:8080/api/v1/{user}/repos
``` 
``user`` to nazwa użytkownika w serwisie GitHub. Odpowiedź na to żądanie dla istniejącego użytkownika zwróci jego repozytoria wraz z ilością ich gwiazdek. 

Dostępne są także opcjonalne parametry. Ich domyślne wartości zostały ustawione na takie same jak wartości domyślne API GitHub. Zostały one dodane w celu możliwości zobaczenia więcej niż 30 repozytoriów z 1 strony jak to jest domyślnie ustawione w API GitHub. Przykładowo allegro posiada w tym momencie 85 publicznych repozytoriów, jednak bez tych parametrów można zobaczyć tylko 30.

| Nazwa  | Opis |Domyślna wartość |
| ------------- | ------------- |------------- |
| page  | numer strony do otrzymania w odpowiedzi  | 1 |
| per_page  | liczba rezultatów na stronę (max 100)  | 30 |

W przypadku wprowadzenia niepoprawnych wartości ``page`` lub ``per_page`` ich wartości są resetowane do wartości domyślnych. Jeżeli na stronie nie będzie jakiegokolwiek repozytorium wówczas aplikacja zwróci pustą odpowiedzi pustą tablicę. 

#### Przykłady poprawnych żądań
``
http://localhost:8080/api/v1/xNakero/repos?per_page=2&page=2 
`` - zwraca repozytoria użytkownika xNakero ze strony 2 przy 2 repozytoriach na stronę

``
http://localhost:8080/api/v1/allegro/repos?per_page=85
`` - zwraca 85 repozytoriów użytkownika allegro ze strony 1 

``
http://localhost:8080/api/v1/allegro/repos?page=2
`` - zwraca 30 repozytoriów użytkownika allegro ze strony 2

``
http://localhost:8080/api/v1/allegro/repos
`` - zwraca 30 repozytoriów użytkownika allegro ze strony 1

### Zwracanie sumy gwiazdek
```
http://localhost:8080/api/v1/{user}/stars
```
``user`` to nazwa użytkownika w serwisie GitHub. Odpowiedź na to żądanie dla istniejącego użytkownika zwróci sumę gwiazdek z wszystkich publicznych repozytoriów użytkownika.

#### Przykład poprawnego żądania
``
http://localhost:8080/api/v1/xNakero/stars
`` - zwraca sumę gwiazdek we wszystkich repozytoriach użytkownika xNakero

## Uwagi
* Uruchomienie według instrukcji było testowane w systemie Windows 10, terminalu Ubuntu for Windows 10 oraz Ubuntu 20.04.2.0 LTS.
* README jest w języku polskim ze względu na to, że rekrutacja odbywa się w tym języku
* Aplikacja zwraca dane dotyczące tylko publicznych repozytoriów

## Propozycje na rozszerzenie projektu

* Dodanie filtrowania oraz sortowania zwracanych repozytoriów
* Rozszerzenie o zwracanie danych prywatnych repozytoriów dla uwierzytelnionych użytkowników
* Pobranie większej ilości właściwości z odpowiedzi z API GitHub
* Zwracanie ilości unikalnych osób dających gwiazdki repozytoriom oraz dotyczących tego statystyk

 
