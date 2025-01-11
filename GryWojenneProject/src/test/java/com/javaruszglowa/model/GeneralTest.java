package com.javaruszglowa.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeneralTest {

    @Test
    void testRecruitSoldier() {
        General general = new General("Alexander", 100);
        general.recruitSoldier(Soldier.Rank.PRIVATE);

        assertEquals(1, general.getArmy().size());
        assertEquals(90, general.getGold());
    }

    @Test
    void testRecruitSoldierInsufficientGold() {
        General general = new General("Alexander", 5);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                general.recruitSoldier(Soldier.Rank.PRIVATE)
        );

        assertTrue(exception.getMessage().contains("Not enough gold to recruit a"));
    }

    @Test
    void testPerformManeuvers() {
        General general = new General("Alexander", 100);
        general.recruitSoldier(Soldier.Rank.CORPORAL); // koszt: 2 * 10 = 20

        general.performManeuvers();

        assertEquals(1, general.getArmy().size());
        assertEquals(1, general.getArmy().get(0).getExperience()); // Doświadczenie wzrosło
        assertEquals(80, general.getGold()); // Gold zmniejszyło się o koszt manewrów
    }

    @Test
    void testPerformManeuversInsufficientGold() {
        General general = new General("Alexander", 0);
        general.recruitSoldier(Soldier.Rank.PRIVATE);

        Exception exception = assertThrows(IllegalStateException.class, general::performManeuvers);

        assertTrue(exception.getMessage().contains("Not enough gold to perform maneuvers"));
    }

    @Test
    void testCalculateArmyStrength() {
        General general = new General("Alexander", 100);
        general.recruitSoldier(Soldier.Rank.PRIVATE);  // Siła: 1 * 1 = 1
        general.recruitSoldier(Soldier.Rank.CAPTAIN); // Siła: 3 * 1 = 3

        int strength = general.calculateArmyStrength();

        assertEquals(4, strength); // Łączna siła armii
    }

    @Test
    void testAttackWin() {
        General attacker = new General("Alexander", 200);
        General defender = new General("Philip", 200);

        attacker.recruitSoldier(Soldier.Rank.CAPTAIN); // Siła: 3
        defender.recruitSoldier(Soldier.Rank.PRIVATE); // Siła: 1

        attacker.attack(defender);

        assertTrue(attacker.getGold() > 200); // Attacker zyskał złoto
        assertTrue(defender.getGold() < 200); // Defender stracił złoto
        assertEquals(2, attacker.getArmy().get(0).getExperience()); // Żołnierze atakującego zyskali doświadczenie
        assertEquals(0, defender.getArmy().get(0).getExperience()); // Żołnierze obrońcy stracili doświadczenie
    }

    @Test
    void testAttackLose() {
        General attacker = new General("Alexander", 200);
        General defender = new General("Philip", 200);

        attacker.recruitSoldier(Soldier.Rank.PRIVATE); // Siła: 1
        defender.recruitSoldier(Soldier.Rank.CAPTAIN); // Siła: 3

        attacker.attack(defender);

        assertTrue(attacker.getGold() < 200); // Attacker stracił złoto
        assertTrue(defender.getGold() > 200); // Defender zyskał złoto
        assertEquals(2, defender.getArmy().get(0).getExperience()); // Żołnierze obrońcy zyskali doświadczenie
        assertEquals(0, attacker.getArmy().get(0).getExperience()); // Żołnierze atakującego stracili doświadczenie
    }

    @Test
    void testAttackDraw() {
        General general1 = new General("Alexander", 200);
        General general2 = new General("Philip", 200);

        general1.recruitSoldier(Soldier.Rank.PRIVATE); // Siła: 1
        general2.recruitSoldier(Soldier.Rank.PRIVATE); // Siła: 1

        general1.attack(general2);

        assertEquals(0, general1.getArmy().size()); // Obaj generałowie stracili po jednym żołnierzu
        assertEquals(0, general2.getArmy().size());
    }

    @Test
    void testSaveAndLoadFromFile() throws IOException {
        General general = new General("Alexander", 300);
        general.recruitSoldier(Soldier.Rank.CAPTAIN);

        String filePath = "test_general.json";
        general.saveToFile(filePath);

        General loadedGeneral = General.loadFromFile(filePath);

        assertEquals(general.getName(), loadedGeneral.getName());
        assertEquals(general.getGold(), loadedGeneral.getGold());
        assertEquals(general.getArmy().size(), loadedGeneral.getArmy().size());

        // Cleanup
        new File(filePath).delete();
    }

    @Test
    void testSaveToCSV() throws IOException {
        General general = new General("Alexander", 300);
        general.recruitSoldier(Soldier.Rank.CAPTAIN);

        String filePath = "test_general.csv";
        general.saveToCSV(filePath);

        File csvFile = new File(filePath);
        assertTrue(csvFile.exists());
        assertTrue(csvFile.length() > 0);

        // Cleanup
        csvFile.delete();
    }
}
