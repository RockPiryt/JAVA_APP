package com.javaruszglowa.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class Soldier {

    //  stopnie wojskowe żołnierza
    public enum Rank {
        PRIVATE(1), CORPORAL(2), CAPTAIN(3), MAJOR(4);

        final int value;

        Rank(int value) {
            this.value = value;
        }
        // pobieranie wartości stopnia
        public int getValue() {
            return value;
        }
    }

    private Rank rank; 
    private int experience; 

    // Domyślny konstruktor wymagany przez Jackson
    @JsonCreator
    public Soldier() {
        this.rank = Rank.PRIVATE;
        this.experience = 0;
    }
    

    //Konstruktor klasy Soldier
    public Soldier(Rank rank, int experience) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null");
        }
        if (experience < 0) {
            throw new IllegalArgumentException("Experience cannot be negative");
        }
        this.rank = rank;
        this.experience = experience;
    }
    

    public Rank getRank() {
        return rank;
    }
    

    public int getExperience() {
        return experience;
    }

    @JsonIgnore 
    public int getStrength() {
        return rank.getValue() * experience;
    }


    public void gainExperience() {
        experience++;
        if (experience >= rank.getValue() * 5) {
            promote();
        }
    }

    private void promote() {
        if (rank.ordinal() < Rank.values().length - 1) {
            rank = Rank.values()[rank.ordinal() + 1];
            experience = 1;
        }
    }

    public void loseExperience() {
        if (experience > 0) {
            experience--;
        }
    }
    

    //Żołnierz jest żywy, jeśli ma doświadczenie większe niż 0.
    @JsonIgnore
    public boolean isAlive() {
        return experience > 0;
    }


    @Override
    public String toString() {
        return "Soldier{" +
                "rank=" + rank +
                ", experience=" + experience +
                '}';
    }
}
