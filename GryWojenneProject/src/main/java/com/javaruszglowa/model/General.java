package com.javaruszglowa.model;

import com.fasterxml.jackson.databind.ObjectMapper; 

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class General {
    private String name;
    private int gold;
    private List<Soldier> army;

    public General(String name, int gold) {
        this.name = name;
        this.gold = gold;
        this.army = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public void removeGold(int amount) {
        gold -= amount;
    }

    public List<Soldier> getArmy() { // Getter for army
        return army;
    }

    // zakup zolnierzy: koszt zolnierza = 10 * (jego stopien); zakupieni zolnierze posiadają doswiadczenie=1
    public void recruitSoldier(Soldier.Rank rank) {
        int cost = rank.value * 10;
        if (gold >= cost) {
            army.add(new Soldier(rank, 1));
            gold -= cost;
        } else {
            throw new IllegalStateException("Not enough gold to recruit a " + rank);
        }
    }

    // zarzadznie manewrow armii które  powiekszaja doswiadczenie zolnierzy o 1: 
    // manewry  kosztuje za kazdego zolnierza bioracego udzial w manewrach 
    // general placi wartosc (liczba monet) przypisana do stopnia wojskowego
    public void performManeuvers() { 
        for (Soldier soldier : army) {
            int cost = soldier.getRank().value;
            if (gold >= cost) {
                soldier.gainExperience();
                gold -= cost;
            } else {
                throw new IllegalStateException("Not enough gold to perform maneuvers.");
            }
        }
    }

    public int calculateArmyStrength() {
        int totalStrength = 0; 
    
        // Iteracja przez każdego żołnierza w liście armii
        for (Soldier soldier : army) {
            totalStrength += soldier.getStrength(); // Dodanie siły żołnierza do całkowitej siły
        }
    
        return totalStrength; 
    }
    
    // - zaatakowanie drugiego generała:
    //  wygrywa generał który posiada armie o większej łacznej sile,
    //   przegrany przekazuje 10% swojego złota wygrywającemu, 
    //   kazdy zolnierz z armii przegrywajacej traci 1 punkt doswiadczenia, a z wygrywajacej zyskuje jeden, 
    //   w przypdaku remisu, kazdy general musi rozstrzelac jednego swojego losowo wybranego zolnierza,
    public void attack(General opponent) {
        // Ocena sił armii
        int myStrength = calculateArmyStrength();
        int opponentStrength = opponent.calculateArmyStrength();
    
        // Wygrana
        if (myStrength > opponentStrength) {
            // Przejęcie 10% złota przeciwnika
            int loot = (int) (opponent.getGold() * 0.1);
            this.addGold(loot);
            opponent.removeGold(loot);
    
            // Zwiększenie doświadczenia każdego żołnierza w armii zwycięzcy
            for (Soldier soldier : army) {
                soldier.gainExperience();
            }
    
            // Zmniejszenie doświadczenia każdego żołnierza w armii przegranego
            for (Soldier soldier : opponent.getArmy()) {
                soldier.loseExperience();
            }
        }
        // Przegrana
        else if (myStrength < opponentStrength) {
            // Przegrany przekazuje 10% swojego złota wygrywającemu
            int loot = (int) (this.gold * 0.1);
            opponent.addGold(loot);
            this.removeGold(loot);
    
            // Zwiększenie doświadczenia każdego żołnierza w armii zwycięzcy
            for (Soldier soldier : opponent.getArmy()) {
                soldier.gainExperience();
            }
    
            // Zmniejszenie doświadczenia każdego żołnierza w armii przegranego
            for (Soldier soldier : army) {
                soldier.loseExperience();
            }
        }
        // Remis
        else {
            // Resmis -Każdy generał musi rozstrzelać jednego losowego żołnierza
            if (!army.isEmpty()) {
                int index = new Random().nextInt(army.size());
                army.remove(index);
            }
    
            if (!opponent.getArmy().isEmpty()) {
                int index = new Random().nextInt(opponent.getArmy().size());
                opponent.getArmy().remove(index);
            }
        }
    }    

    //- general wraz ze swoimi zasobami powinien mieć mozliwosc zapisu do json lub csv  i odczytu swojego stanu na / z dysku
    public void saveToFile(String filePath) throws IOException {
        // Używamy ObjectMapper, aby zapisać dane obiektu w pliku JSON
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
    
        // Zapisujemy dane do pliku
        mapper.writeValue(file, this);
    }
    
    public static General loadFromFile(String filePath) throws IOException {
        // Używamy ObjectMapper, aby odczytać dane z pliku JSON
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
    
        // Konwertujemy dane JSON na obiekt General
        General general = mapper.readValue(file, General.class);
        return general;
    }
    
    public void saveToCSV(String filePath) throws IOException {
        // Otwieramy plik do zapisu
        FileWriter fileWriter = new FileWriter(filePath);
    
        // Zapisujemy nagłówki w CSV
        fileWriter.write("Name,Gold,Army\n");
    
        // Zapisujemy dane obiektu w formacie CSV
        fileWriter.write(name + "," + gold + "," + army.toString() + "\n");
    
        // Zamykamy plik
        fileWriter.close();
    }
    

    @Override
    public String toString() {
        return "General{" +
                "name='" + name + '\'' +
                ", gold=" + gold +
                ", army=" + army +
                '}';
    }
}
