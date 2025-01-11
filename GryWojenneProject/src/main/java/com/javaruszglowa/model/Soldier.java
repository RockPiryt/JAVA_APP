package com.javaruszglowa.model;

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

    public int getStrength() {
        return rank.value * experience;
    }


    public void gainExperience() {
        experience++;
        if (experience >= rank.getValue() * 5) { 
            promote();
        }
    }
    

    public void loseExperience() {
        experience = Math.max(0, experience - 1); // Doświadczenie zmniejsza się o 1, ale nie spada poniżej 0
    }

    //Żołnierz jest żywy, jeśli ma doświadczenie większe niż 0.
    public boolean isAlive() {
        return experience > 0;
    }

    /**
     * Awansuje żołnierza na wyższy stopień.
     * Jeśli żołnierz osiągnął najwyższy stopień (MAJOR), nie może już awansować.
     * Po awansie doświadczenie zostaje zresetowane do 1.
     */
    private void promote() {
        if (rank.ordinal() < Rank.values().length - 1) {
            rank = Rank.values()[rank.ordinal() + 1]; 
            experience = 1; 
        }
    }


    @Override
    public String toString() {
        return "Soldier{" +
                "rank=" + rank +
                ", experience=" + experience +
                '}';
    }
}
