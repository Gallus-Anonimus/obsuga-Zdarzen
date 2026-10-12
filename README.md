# Zadanie 2 - Zgadywanka

## Opis aplikacji

Zgadywanka to prosta aplikacja napisana w języku Java na platformę Android.

Aplikacja losuje liczbę z zakresu od 1 do 100. Użytkownik wpisuje swoją propozycję i naciska przycisk „Sprawdź”.

Aplikacja informuje, czy podana liczba jest za mała, za duża czy poprawna. Zlicza również liczbę prób. Przycisk „Nowa gra” losuje kolejną liczbę i zeruje licznik prób.

## Zrzut ekranu

![Zrzut ekranu aplikacji Zgadywanka](docs/gussTheNumberGame.png)

## Obsługa zdarzeń

W aplikacji zastosowano dwa sposoby podpinania obsługi kliknięć.

### 1. Wspólny listener

W pliku `MainActivity.java` utworzono listener `gameListener`:



private final View.OnClickListener gameListener = view -> checkGuess();


Listener jest podpinany do przycisku „Sprawdź” w metodzie `onCreate()`:



btnGuess.setOnClickListener(gameListener);


Dzięki temu obsługa kliknięcia jest oddzielona od kodu konfigurującego interfejs.

### 2. Anonimowa klasa OnClickListener

Przycisk „Nowa gra” korzysta z anonimowej klasy `View.OnClickListener`, również w metodzie `onCreate()`:



btnNewGame.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { startNewGame(); } });


Po kliknięciu przycisku wywoływana jest metoda `startNewGame()`.

## Walidacja i obsługa błędów

Aplikacja sprawdza, czy pole nie jest puste oraz czy wpisana liczba należy do zakresu od 1 do 100.

W przypadku pustego pola, niepoprawnej liczby lub próby wykonania ruchu po zakończeniu gry wyświetlany jest komunikat `Toast`. Aplikacja nie kończy działania z powodu niepoprawnych danych.

## Organizacja kodu

Metoda `onCreate()` inicjalizuje widoki i podpina zdarzenia. Logika gry znajduje się w osobnych metodach:

- `startNewGame()` - losowanie liczby i reset stanu gry;
- `checkGuess()` - sprawdzanie propozycji i aktualizacja licznika prób.

Teksty interfejsu i komunikaty przechowywane są w `res/values/strings.xml`.