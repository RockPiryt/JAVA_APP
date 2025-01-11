package com.javaruszglowa.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class General extends Soldier {
    private String name;
    private int gold;
    private List<Soldier> army;

    // Domyślny konstruktor wymagany przez Jackson
    @JsonCreator
    public General() {
        super(Rank.MAJOR, 5); // Domyślny stopień dla generała
        this.name = "Unknown"; // Domyślna nazwa
        this.gold = 0; // Domyślna wartość złota
        this.army = new ArrayList<>(); // Pusta lista armii
    }

    // Konstruktor z parametrami
    public General(String name, int gold) {
        super(Rank.MAJOR, 5); // Generał zawsze zaczyna na stopniu MAJOR z maksymalnym doświadczeniem
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
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative gold");
        }
        gold += amount;
    }

    public void removeGold(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot remove negative gold");
        }
        if (gold < amount) {
            throw new IllegalStateException("Not enough gold to remove the specified amount");
        }
        gold -= amount;
    }

    public List<Soldier> getArmy() { // Getter for army
        return army;
    }

    // zakup zolnierzy: koszt zolnierza = 10 * (jego stopien); zakupieni zolnierze posiadają doswiadczenie=1
    public void recruitSoldier(Soldier.Rank rank) {
        int cost = rank.getValue()* 10;
        if (cost < 0) {
            throw new IllegalArgumentException("Cost of recruiting cannot be negative");
        }
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
            int cost = soldier.getRank().getValue(); 
            if (cost < 0) {
                throw new IllegalArgumentException("Cost of maneuvers cannot be negative");
            }
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

        for (Soldier soldier : army) {
            totalStrength += soldier.getStrength();
        }

        return totalStrength;
    }
    
    // - zaatakowanie drugiego generała:
    //  wygrywa generał który posiada armie o większej łacznej sile,
    //   przegrany przekazuje 10% swojego złota wygrywającemu, 
    //   kazdy zolnierz z armii przegrywajacej traci 1 punkt doswiadczenia, a z wygrywajacej zyskuje jeden, 
    //   w przypdaku remisu, kazdy general musi rozstrzelac jednego swojego losowo wybranego zolnierza,
    public void attack(General opponent) {
        // Obliczenie łącznej siły armii obu generałów
        int myStrength = calculateArmyStrength();
        int opponentStrength = opponent.calculateArmyStrength();
    
        if (myStrength > opponentStrength) {
            // --------------------------
            // Wygrana atakującego (this)
            // --------------------------
            // Przegrany = obrońca, więc obrońca płaci 10% *swojego* złota
            int loot = Math.max(1, (int) (opponent.getGold() * 0.1));
            this.addGold(loot);
            opponent.removeGold(loot);
    
            // Zwycięzca zyskuje doświadczenie
            for (Soldier soldier : this.army) {
                soldier.gainExperience();
            }
            // Przegrany traci doświadczenie
            for (Soldier soldier : opponent.getArmy()) {
                soldier.loseExperience();
            }
    
        } else if (myStrength < opponentStrength) {
            // --------------------------
            // Wygrana obrońcy (opponent)
            // --------------------------
            // Przegrany = atakujący (this), więc atakujący płaci 10% *swojego* złota
            int loot = Math.max(1, (int) (this.getGold() * 0.1));
            opponent.addGold(loot);
            this.removeGold(loot);
    
            // Zwycięzca zyskuje doświadczenie
            for (Soldier soldier : opponent.getArmy()) {
                soldier.gainExperience();
            }
            // Przegrany traci doświadczenie
            for (Soldier soldier : this.army) {
                soldier.loseExperience();
            }
    
        } else {
            // ----------------
            // Remis (myStrength == opponentStrength)
            // ----------------
            // Każdy generał traci jednego losowego żołnierza (o ile ma jakiegoś)
            if (!this.army.isEmpty()) {
                int index = new Random().nextInt(this.army.size());
                this.army.remove(index);
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
                ", rank=" + getRank() +
                ", experience=" + getExperience() +
                '}';
    }
}
