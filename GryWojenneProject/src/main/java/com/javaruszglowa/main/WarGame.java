package com.javaruszglowa.main;

import java.io.IOException;

import com.javaruszglowa.model.GameManager;
import com.javaruszglowa.model.Secretary;
import com.javaruszglowa.model.Soldier;
// import com.javaruszglowa.model.Soldier.Rank;
import com.javaruszglowa.model.General;


public class WarGame {
    public static void main(String[] args) throws IOException {
        General general1 = new General("General A", 100);
        General general2 = new General("General B", 100);
        Secretary secretary = new Secretary();
        GameManager gameManager = new GameManager(general1, general2, secretary);

        // Example gameplay
        gameManager.performAction("Recruit soldiers for General A", () -> {
            general1.recruitSoldier(Soldier.Rank.PRIVATE);
            general1.recruitSoldier(Soldier.Rank.CAPTAIN);
        });

        gameManager.performAction("Recruit soldiers for General B", () -> {
            general2.recruitSoldier(Soldier.Rank.MAJOR);
            general2.recruitSoldier(Soldier.Rank.CORPORAL);
        });

        gameManager.performAction("General A maneuvers", general1::performManeuvers);

        gameManager.performAction("General A attacks General B", () -> {
            general1.attack(general2);
        });

        gameManager.saveGame("war_game_save");

        secretary.printLogs();
    }
}

// public class Main {
//     public static void main(String[] args) {
//         Secretary secretary = new Secretary();

//         // Dodawanie logów
//         secretary.logAction("Generał Alexander rekrutował żołnierza.");
//         secretary.logAction("Generał Philip wykonał manewry wojskowe.");
//         secretary.logAction("Generał Alexander zaatakował generała Philip.");

//         // Wyświetlanie logów
//         secretary.printLogs();

//         // Zapisywanie logów do pliku
//         secretary.saveLogs("logs.txt");
//     }
// }

// import com.javaruszglowa.model.Secretary;

// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileReader;
// import java.io.IOException;

// public class Main {
//     public static void main(String[] args) {
//         Secretary secretary = new Secretary();

//         // Dodawanie logów
//         secretary.logAction("Generał Alexander rekrutował żołnierza.");
//         secretary.logAction("Generał Philip wykonał manewry wojskowe.");
//         secretary.logAction("Generał Alexander zaatakował generała Philip.");

//         // Wyświetlanie logów
//         System.out.println("Logi przed zapisaniem do pliku:");
//         secretary.printLogs();

//         // Ścieżka do pliku
//         String filePath = "logs.txt";

//         // Zapisywanie logów do pliku
//         try {
//             secretary.saveLogs(filePath);
//             System.out.println("\nLogi zapisane do pliku: " + filePath);

//             // Sprawdzanie, czy plik istnieje
//             File file = new File(filePath);
//             if (file.exists()) {
//                 System.out.println("Plik istnieje: " + filePath);

//                 // Sprawdzanie zawartości pliku
//                 System.out.println("\nZawartość pliku:");
//                 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                     String line;
//                     while ((line = reader.readLine()) != null) {
//                         System.out.println(line);
//                     }
//                 }
//             } else {
//                 System.err.println("Plik nie został utworzony!");
//             }
//         } catch (IOException e) {
//             System.err.println("Błąd podczas zapisywania logów: " + e.getMessage());
//         }
//     }
// }



