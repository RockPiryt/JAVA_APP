package com.javaruszglowa.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SecretaryTest {

    private Secretary secretary;
    private final String testFilePath = "test_logs.txt";

    @BeforeEach
    void setUp() {
        // Inicjalizacja obiektu Secretary przed każdym testem
        secretary = new Secretary();
    }

    @AfterEach
    void tearDown() {
        // Usuwanie pliku testowego po każdym teście
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testLogAction() {
        secretary.logAction("Test log 1");
        secretary.logAction("Test log 2");

        // Sprawdzenie, czy logi zostały dodane
        assertEquals(2, secretary.getLogs().size());
        assertEquals("Test log 1", secretary.getLogs().get(0));
        assertEquals("Test log 2", secretary.getLogs().get(1));
    }


    @Test
    void testSaveLogs() throws IOException {
        // Dodanie logów
        secretary.logAction("Test log 1");
        secretary.logAction("Test log 2");

        // Zapis logów do pliku
        secretary.saveLogs(testFilePath);

        // Sprawdzenie, czy plik został utworzony
        File testFile = new File(testFilePath);
        assertTrue(testFile.exists());

        // Weryfikacja zawartości pliku
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            assertEquals("Test log 1", reader.readLine());
            assertEquals("Test log 2", reader.readLine());
            assertNull(reader.readLine()); // Brak dodatkowych linii
        }
    }

    @Test
    void testPrintLogs() {
        // Dodanie logów
        secretary.logAction("Test log 1");
        secretary.logAction("Test log 2");

        // Wywołanie metody printLogs
        // Trudne do przetestowania wprost, ale można zweryfikować poprawność logów
        assertEquals(2, secretary.logs.size());
        assertEquals("Test log 1", secretary.logs.get(0));
        assertEquals("Test log 2", secretary.logs.get(1));
    }

    @Test
    void testSaveLogsEmpty() throws IOException {
        // Próba zapisu pustych logów
        secretary.saveLogs(testFilePath);

        // Sprawdzenie, czy plik został utworzony
        File testFile = new File(testFilePath);
        assertTrue(testFile.exists());

        // Weryfikacja, że plik jest pusty
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            assertNull(reader.readLine()); // Plik nie powinien zawierać żadnych linii
        }
    }
}

