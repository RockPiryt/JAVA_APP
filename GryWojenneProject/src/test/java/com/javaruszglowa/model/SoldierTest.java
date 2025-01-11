package com.javaruszglowa.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoldierTest {

    @Test
    void testGetRank() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 1);
        assertEquals(Soldier.Rank.PRIVATE, soldier.getRank());
    }

    @Test
    void testGetExperience() {
        Soldier soldier = new Soldier(Soldier.Rank.CORPORAL, 3);
        assertEquals(3, soldier.getExperience());
    }

    @Test
    void testGetStrength() {
        Soldier soldier = new Soldier(Soldier.Rank.CAPTAIN, 2);
        assertEquals(6, soldier.getStrength()); // CAPTAIN (value = 3) * experience (2) = 6
    }

    @Test
    void testGainExperienceWithoutPromotion() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 4);
        soldier.gainExperience(); // 4 -> 5, ale nie osiąga limitu promocji
        assertEquals(5, soldier.getExperience());
        assertEquals(Soldier.Rank.PRIVATE, soldier.getRank());
    }

    @Test
    void testGainExperienceWithPromotion() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 5); // PRIVATE (1 * 5 = 5, kwalifikacja do awansu)
        soldier.gainExperience();
        assertEquals(Soldier.Rank.CORPORAL, soldier.getRank());
        assertEquals(1, soldier.getExperience()); // Po awansie doświadczenie resetuje się do 1
    }

    @Test
    void testLoseExperience() {
        Soldier soldier = new Soldier(Soldier.Rank.CAPTAIN, 3);
        soldier.loseExperience();
        assertEquals(2, soldier.getExperience());
    }

    @Test
    void testLoseExperienceToZero() {
        Soldier soldier = new Soldier(Soldier.Rank.CAPTAIN, 1);
        soldier.loseExperience(); // 1 -> 0
        assertEquals(0, soldier.getExperience());
        soldier.loseExperience(); // Nie może zejść poniżej 0
        assertEquals(0, soldier.getExperience());
    }

    @Test
    void testPromoteToHighestRank() {
        Soldier soldier = new Soldier(Soldier.Rank.MAJOR, 20); // MAJOR (4 * 5 = 20, kwalifikacja do awansu)
        soldier.gainExperience();
        assertEquals(Soldier.Rank.MAJOR, soldier.getRank()); // Nie może awansować, bo MAJOR to najwyższy stopień
        assertEquals(21, soldier.getExperience()); // Doświadczenie nadal rośnie
    }

    @Test
    void testIsAliveTrue() {
        Soldier soldier = new Soldier(Soldier.Rank.CORPORAL, 1);
        assertTrue(soldier.isAlive());
    }

    @Test
    void testIsAliveFalse() {
        Soldier soldier = new Soldier(Soldier.Rank.CORPORAL, 0);
        assertFalse(soldier.isAlive());
    }

    @Test
    void testToString() {
        Soldier soldier = new Soldier(Soldier.Rank.PRIVATE, 2);
        String expected = "Soldier{rank=PRIVATE, experience=2}";
        assertEquals(expected, soldier.toString());
    }
}
