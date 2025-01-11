package com.javaruszglowa.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Secretary {
    private final List<LogEntry> logs; // Lista przechowująca logi jako obiekty LogEntry

    public Secretary() {
        logs = new ArrayList<>(); // Inicjalizacja pustej listy logów
    }

    // Klasa wewnętrzna do reprezentacji logów
    public static class LogEntry {
        private final LocalDateTime timestamp;
        private final String action;

        public LogEntry(String action) {
            if (action == null || action.trim().isEmpty()) {
                throw new IllegalArgumentException("Action cannot be null or empty");
            }
            this.timestamp = LocalDateTime.now();
            this.action = action;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getAction() {
            return action;
        }

        @Override
        public String toString() {
            return "[" + timestamp + "] " + action;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            LogEntry logEntry = (LogEntry) obj;
            return action.equals(logEntry.action) && timestamp.equals(logEntry.timestamp);
        }

        @Override
        public int hashCode() {
            return action.hashCode() + timestamp.hashCode();
        }
    }

    // Dodanie nowego logu
    public void logAction(String action) {
        logs.add(new LogEntry(action));
    }

    // Zapis logów do pliku tekstowego
    public void saveLogs(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (LogEntry log : logs) {
                writer.write(log.toString() + System.lineSeparator());
            }
            System.out.println("Logi zapisane do pliku: " + filePath);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania logów: " + e.getMessage());
        }
    }

    // Zapis logów do pliku JSON
    public void saveLogsToJSON(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new java.io.File(filePath), logs);
            System.out.println("Logi zapisane do pliku JSON: " + filePath);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania logów w formacie JSON: " + e.getMessage());
        }
    }

    // Filtrowanie logów według słowa kluczowego
    public List<LogEntry> filterLogs(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be null or empty");
        }
        return logs.stream()
                .filter(log -> log.getAction().contains(keyword))
                .collect(Collectors.toList());
    }

    // Wyświetlanie logów w konsoli
    public void printLogs() {
        if (logs.isEmpty()) {
            System.out.println("Brak logów do wyświetlenia.");
        } else {
            System.out.println("Lista logów:");
            logs.forEach(System.out::println);
        }
    }

    // Getter dla pola logs
    public List<LogEntry> getLogs() {
        return new ArrayList<>(logs); // Zwraca kopię listy logów, aby zabezpieczyć oryginalne dane
    }
}
