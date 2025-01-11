//Moduł logowania (Secretary) - śledzi działania generałów i zapisuje je do pliku.
package com.javaruszglowa.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Secretary {
    public List<String> logs; // Lista przechowująca logi

    public Secretary() {
        logs = new ArrayList<>(); // Inicjalizacja pustej listy logów
    }

    // Metoda do dodawania logów
    public void logAction(String action) {
        logs.add(action); // Dodaje nowy log do listy
    }

    // Metoda do zapisywania logów do pliku
    public void saveLogs(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String log : logs) {
                writer.write(log + "\n"); // Zapisuje każdy log w nowej linii
            }
            System.out.println("Logi zapisane do pliku: " + filePath);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania logów: " + e.getMessage());
        }
    }

    // Metoda do wyświetlania logów w konsoli
    public void printLogs() {
        System.out.println("Lista logów:");
        for (String log : logs) {
            System.out.println(log); // Wyświetla każdy log w nowej linii
        }
    }

    // Getter dla pola logs
    public List<String> getLogs() {
        return logs;
    }
}
