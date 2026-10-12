# Kalkulator - refaktoryzacja

## Etap 1 - osobna metoda dla każdej cyfry

Na tym etapie każda cyfra ma własną metodę. Jeśli chcemy ograniczyć długość wyświetlanej liczby do 10 znaków, musimy wprowadzić odpowiednie sprawdzenie w 10 miejscach - po jednym dla każdej cyfry.

## Etap 2 - jedna metoda z parametrem

Zamiast osobnych metod dla cyfr korzystamy z jednej metody `appendSymbol(String symbol)`, która przyjmuje cyfrę jako parametr. Dzięki temu kod jest krótszy i łatwiejszy w utrzymaniu.

## Etap 3 - wspólny listener

Wszystkie przyciski cyfr korzystają ze wspólnego listenera `sharedListener`, który przekazuje tekst klikniętego przycisku do metody `appendSymbol()`.

Jeśli chcemy ograniczyć długość wyświetlanej liczby do 10 znaków, wystarczy wprowadzić sprawdzenie w jednym miejscu - w metodzie `appendSymbol()`.

## Odpowiedź na pytanie z zadania

- **Etap 1: 10 miejsc w kodzie** - każde z dziesięciu przycisków cyfr wymagałoby osobnej zmiany.
- **Etap 3: 1 miejsce w kodzie** - wystarczy zmienić wspólną metodę `appendSymbol()`.

Refaktoryzacja zmniejsza liczbę miejsc wymagających zmian z 10 do 1, dzięki czemu kod jest prostszy i łatwiejszy w utrzymaniu.

## Obsługa działań i błędów

Kalkulator obsługuje dodawanie, odejmowanie, mnożenie i dzielenie, a także liczby dziesiętne. Przycisk `=` oblicza wynik, a przycisk `C` czyści wyświetlacz i przywraca stan początkowy.

W przypadku dzielenia przez zero lub niepoprawnego wyniku na wyświetlaczu pojawia się komunikat `Error`.

## Limit wyświetlacza

Wyświetlacz obsługuje maksymalnie 10 znaków dla bieżącej liczby. Dłuższe wyniki są przedstawiane w notacji naukowej, jeśli mieszczą się w limicie.

## Poprawka zera wiodącego

W metodzie `appendSymbol()` dodano warunek, który zastępuje początkowe zero wpisywaną cyfrą. Dzięki temu po naciśnięciu `0`, a następnie `5`, wyświetlacz pokazuje `5` zamiast `05`.