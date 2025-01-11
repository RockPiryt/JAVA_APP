package com.javaruszglowa.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void testGainExperienceWithoutPromotion() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 1);
        for (int i = 0; i < 4; i++) {
            soldier.gainExperience();
        }
        assertEquals(5, soldier.getExperience(), "Doświadczenie powinno wynosić 5 po 4-krotnym wywołaniu gainExperience");
    }

    @Test
    void testGainExperienceWithPromotion() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 5);
        soldier.gainExperience();
        assertAll(
            () -> assertEquals(Soldier.Rank.CORPORAL, soldier.getRank(), "Po awansie stopień powinien być CORPORAL"),
            () -> assertEquals(1, soldier.getExperience(), "Po awansie doświadczenie powinno wynosić 1")
        );
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
    void testPromoteToHighestRank() {
        Soldier soldier = new Soldier(Soldier.Rank.MAJOR, 20);
        soldier.gainExperience();
        assertAll(
            () -> assertEquals(Soldier.Rank.MAJOR, soldier.getRank(), "Stopień powinien pozostać MAJOR, bo to najwyższy stopień"),
            () -> assertEquals(21, soldier.getExperience(), "Doświadczenie powinno nadal rosnąć po osiągnięciu limitu")
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
}
