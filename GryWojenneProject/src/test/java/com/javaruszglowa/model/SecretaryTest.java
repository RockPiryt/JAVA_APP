package com.javaruszglowa.model;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SecretaryTest {

    @Test
    void testLogAction() {
        Secretary secretary = new Secretary();
        secretary.logAction("Test log");

        assertEquals(1, secretary.getLogs().size());
        assertTrue(secretary.getLogs().get(0).getAction().contains("Test log"));
    }

    @Test
    void testFilterLogs() {
        Secretary secretary = new Secretary();
        secretary.logAction("Generał Alexander rekrutował żołnierza.");
        secretary.logAction("Generał Philip zaatakował przeciwnika.");

        List<Secretary.LogEntry> filtered = secretary.filterLogs("zaatakował");
        assertEquals(1, filtered.size());
        assertTrue(filtered.get(0).getAction().contains("zaatakował"));
    }

    @Test
    void testSaveLogsToJSON() {
        Secretary secretary = new Secretary();
        secretary.logAction("Log testowy");

        secretary.saveLogsToJSON("test_logs.json");

        java.io.File file = new java.io.File("test_logs.json");
        assertTrue(file.exists());
        file.delete(); // Usuwamy plik testowy
    }
}
