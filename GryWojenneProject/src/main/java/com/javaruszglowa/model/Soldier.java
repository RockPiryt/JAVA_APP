package com.javaruszglowa.model;

public class Soldier {
    public enum Rank {
        PRIVATE(1), CORPORAL(2), CAPTAIN(3), MAJOR(4);

        final int value;
        Rank(int value) { this.value = value; }
    }

    private Rank rank;
    private int experience;

    public Soldier(Rank rank, int experience) {
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

    //jeżeli doswiadczenie osiagnie pięciokrotonosc wartość stopnia, awansuje na kolejny stopien oraz jego dosiwadczene=1
    public void gainExperience() {
        experience++;
        if (experience >= rank.value * 5) {
            promote();
        }
    }

    public void loseExperience() {
        experience = Math.max(0, experience - 1);
        // Funkcja Math.max(a, b) zwraca większą z dwóch liczb.
    }

    // Warunek sprawdza, czy aktualny stopień żołnierza nie jest już najwyższym stopniem. Jeśli jest, awansowanie jest niemożliwe.
    private void promote() {
        // rank.ordinal() zwraca indeks aktualnego stopnia (np. PRIVATE ma indeks 0, CORPORAL ma indeks 1 itd.).
        if (rank.ordinal() < Rank.values().length - 1) {
            rank = Rank.values()[rank.ordinal() + 1];
            experience = 1;
        }
    }

    public boolean isAlive() {
        return experience > 0;
    }

    @Override
    public String toString() {
        return "Soldier{" + "rank=" + rank + ", experience=" + experience + '}';
    }
}
