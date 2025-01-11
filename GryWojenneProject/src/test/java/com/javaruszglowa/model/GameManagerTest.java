package com.javaruszglowa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private General general1;
    private General general2;
    private Secretary secretary;
    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        // Tworzymy mocki dla General i Secretary
        general1 = mock(General.class);
        general2 = mock(General.class);
        secretary = mock(Secretary.class);

        // Tworzymy GameManager z mockami
        gameManager = new GameManager(general1, general2, secretary);
    }

    @Test
    void testPerformActionSuccess() {
        // Wykonanie prostej akcji
        gameManager.performAction("Recruit Soldier", () -> {
            general1.recruitSoldier(Soldier.Rank.PRIVATE);
        });

        // Sprawdzenie, czy logAction został wywołany z odpowiednim argumentem
        verify(secretary).logAction("Recruit Soldier");
    }

    @Test
    void testPerformActionFailure() {
        // Akcja, która rzuca wyjątek
        doThrow(new IllegalStateException("Not enough gold"))
            .when(general1).recruitSoldier(Soldier.Rank.PRIVATE);

        gameManager.performAction("Recruit Soldier", () -> {
            general1.recruitSoldier(Soldier.Rank.PRIVATE);
        });

        // Sprawdzenie, czy logAction zarejestrował błąd
        verify(secretary).logAction("Action failed: Recruit Soldier - Not enough gold");
    }

    @Test
    void testSaveGame() throws IOException {
        // Wywołanie metody saveGame
        gameManager.saveGame("test_game");

        // Sprawdzenie, czy metody zapisu zostały wywołane dla każdego generała i sekretarza
        verify(general1).saveToFile("test_game_general1.json");
        verify(general2).saveToFile("test_game_general2.json");
        verify(secretary).saveLogs("test_game_logs.txt");
    }

    @Test
    void testSaveGameFailure() throws IOException {
        // Symulowanie błędu przy zapisie stanu generała
        doThrow(new IOException("File write error")).when(general1).saveToFile(anyString());

        IOException exception = assertThrows(IOException.class, () -> {
            gameManager.saveGame("test_game");
        });

        // Sprawdzenie komunikatu błędu
        assertEquals("File write error", exception.getMessage());
    }
}
