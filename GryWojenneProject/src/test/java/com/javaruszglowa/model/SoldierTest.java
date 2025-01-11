package com.javaruszglowa.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

class SoldierTest {

    @Test
    void testGetRank() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 1);
        assertEquals(Soldier.Rank.PRIVATE, soldier.getRank(), "Stopień żołnierza powinien być PRIVATE");
    }

    @Test
    void testGetExperience() {
        Soldier soldier = new Soldier(Soldier.Rank.CORPORAL, 3);
        assertEquals(3, soldier.getExperience(), "Doświadczenie żołnierza powinno wynosić 3");
    }

    @Test
    void testGetStrength() {
        Soldier soldier = new Soldier(Soldier.Rank.CAPTAIN, 2);
        assertEquals(6, soldier.getStrength(), "Siła powinna być obliczona jako rank.value * experience (3 * 2 = 6)");
    }

    @Test
    void testLoseExperience() {
        Soldier soldier = new Soldier(Soldier.Rank.CAPTAIN, 3);
        soldier.loseExperience();
        assertEquals(2, soldier.getExperience(), "Doświadczenie powinno zmniejszyć się do 2");
    }

    @Test
    void testLoseExperienceToZero() {
        Soldier soldier = new Soldier(Soldier.Rank.CAPTAIN, 1);
        soldier.loseExperience();
        soldier.loseExperience();
        assertEquals(0, soldier.getExperience(), "Doświadczenie nie powinno spaść poniżej 0");
    }

    @Test
    void testGainExperienceAtHighestRank() {
        Soldier soldier = new Soldier(Soldier.Rank.MAJOR, 20);

        // Wywołujemy gainExperience kilka razy
        for (int i = 0; i < 5; i++) {
            soldier.gainExperience();
        }

        // Sprawdzamy, że stopień pozostaje MAJOR, a doświadczenie zwiększa się
        assertAll(
            () -> assertEquals(Soldier.Rank.MAJOR, soldier.getRank(), "Stopień powinien pozostać MAJOR, bo to najwyższy stopień"),
            () -> assertEquals(25, soldier.getExperience(), "Doświadczenie powinno zwiększać się powyżej progu awansu")
        );
    }


    @Test
    void testIsAliveTrue() {
        Soldier soldier = new Soldier(Soldier.Rank.CORPORAL, 1);
        assertTrue(soldier.isAlive(), "Żołnierz z doświadczeniem większym niż 0 powinien być żywy");
    }

    @Test
    void testIsAliveFalse() {
        Soldier soldier = new Soldier(Soldier.Rank.CORPORAL, 0);
        assertFalse(soldier.isAlive(), "Żołnierz z doświadczeniem 0 powinien być martwy");
    }

    @Test
    void testToString() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 2);
        String expected = "Soldier{rank=PRIVATE, experience=2}";
        assertEquals(expected, soldier.toString(), "Metoda toString powinna zwrócić poprawny format tekstowy");
    }

    @Test
    void testInvalidRank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Soldier(null, 1)
        );
        assertEquals("Rank cannot be null", exception.getMessage(), "Powinien zostać rzucony wyjątek z odpowiednią wiadomością");
    }

    @Test
    void testNegativeExperience() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Soldier(Soldier.Rank.PRIVATE, -1)
        );
        assertEquals("Experience cannot be negative", exception.getMessage(), "Powinien zostać rzucony wyjątek z odpowiednią wiadomością");
    }

     @Test
    @DisplayName("gainExperience powinien zwiększyć doświadczenie o 1")
    void gainExperienceShouldIncrementExperience() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 0);
        soldier.gainExperience();
        assertEquals(1, soldier.getExperience(), 
                "Experience should be incremented by 1");
    }

    @Test
    @DisplayName("gainExperience powinien promować, gdy doświadczenie osiągnie próg rank.getValue() * 5")
    void gainExperienceShouldPromoteWhenThresholdReached() {
        // Dla rangi PRIVATE getValue() = 1, próg = 5
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 4);
        soldier.gainExperience(); // teraz experience = 5
        // Powinno dojść do promocji do CORPORAL (experience reset na 1)
        assertEquals(Soldier.Rank.CORPORAL, soldier.getRank(), 
                "Rank should be promoted to CORPORAL");
        assertEquals(1, soldier.getExperience(), 
                "Experience should reset to 1 after promotion");
    }

    @Test
    @DisplayName("gainExperience nie powinien promować, gdy nie osiągnięto progu")
    void gainExperienceShouldNotPromoteIfThresholdNotReached() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 3);
        soldier.gainExperience(); // teraz experience = 4
        // Próg dla PRIVATE wynosi 5, więc jeszcze bez promocji
        assertEquals(Soldier.Rank.PRIVATE, soldier.getRank(),
                "Rank should remain PRIVATE");
        assertEquals(4, soldier.getExperience(),
                "Experience should be incremented but no promotion yet");
    }

    @Test
    @DisplayName("Nie powinien przekroczyć najwyższego stopnia")
    void shouldNotExceedMaxRank() {
        // Ostatni stopień to MAJOR
        Soldier soldier = new Soldier(Soldier.Rank.MAJOR, 19);
        // gainExperience wywoła promote() tylko, jeśli ordinal() < Rank.values().length - 1
        // Ale MAJOR to ostatnia ranga, więc nie powinno być promocji
        soldier.gainExperience();
        assertEquals(Soldier.Rank.MAJOR, soldier.getRank(), 
                "Rank should remain MAJOR (the highest rank)");
        // Experience powinno się tylko zwiększyć o 1
        assertEquals(20, soldier.getExperience(),
                "Experience should increment even if rank doesn't change");
    }
}
