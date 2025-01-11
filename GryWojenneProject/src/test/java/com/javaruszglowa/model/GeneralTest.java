package com.javaruszglowa.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeneralTest {

    @Test
    void testRecruitSoldier() {
        // Testuje rekrutację żołnierza
        General general = new General("Alexander", 100);
        general.recruitSoldier(Soldier.Rank.PRIVATE);

        assertAll(
            // Sprawdza, czy armia zawiera 1 żołnierza
            () -> assertEquals(1, general.getArmy().size(), "Armia powinna mieć 1 żołnierza po rekrutacji"),
            // Sprawdza, czy złoto generała zmniejszyło się o koszt żołnierza
            () -> assertEquals(90, general.getGold(), "Złoto powinno zmniejszyć się o koszt żołnierza")
        );
    }

    @Test
    void testRecruitSoldierInsufficientGold() {
        // Testuje próbę rekrutacji żołnierza, gdy brakuje złota
        General general = new General("Alexander", 5);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                general.recruitSoldier(Soldier.Rank.PRIVATE)
        );

        // Sprawdza, czy wyjątek zawiera odpowiednią wiadomość
        assertTrue(exception.getMessage().contains("Not enough gold to recruit a"));
    }

    @Test
    void testPerformManeuvers() {
        General general = new General("Alexander", 100);
        general.recruitSoldier(Soldier.Rank.CORPORAL); // Koszt rekrutacji: 20
        general.performManeuvers(); // Koszt manewrów: 2
    
        assertAll(
            () -> assertEquals(1, general.getArmy().size(), "Armia powinna zawierać 1 żołnierza"),
            () -> assertEquals(2, general.getArmy().get(0).getExperience(), "Doświadczenie żołnierza powinno wzrosnąć o 1"),
            () -> assertEquals(78, general.getGold(), "Złoto powinno zmniejszyć się o koszt manewrów")
        );
    }
    


    @Test
    void testPerformManeuversInsufficientGold() {
        // Mamy 10 zł, akurat na rekrutację PRIVATE.
        General general = new General("Alexander", 10);
        general.recruitSoldier(Soldier.Rank.PRIVATE); // Koszt 10, złoto = 0
    
        Exception exception = assertThrows(IllegalStateException.class, general::performManeuvers);
    
        // Teraz komunikat będzie "Not enough gold to perform maneuvers"
        assertTrue(exception.getMessage().contains("Not enough gold to perform maneuvers"));
    }
    

    @Test
    void testCalculateArmyStrength() {
        // Testuje obliczanie siły armii
        General general = new General("Alexander", 100);
        general.recruitSoldier(Soldier.Rank.PRIVATE);  // Siła: 1 * 1 = 1
        general.recruitSoldier(Soldier.Rank.CAPTAIN); // Siła: 3 * 1 = 3

        int strength = general.calculateArmyStrength();

        // Sprawdza, czy łączna siła armii wynosi 4
        assertEquals(4, strength, "Siła armii powinna wynosić sumę sił żołnierzy");
    }

    @Test
    void testAttackWin() {
        General attacker = new General("Alexander", 200);
        General defender = new General("Philip", 200);

        attacker.recruitSoldier(Soldier.Rank.CAPTAIN); // Siła: 3
        defender.recruitSoldier(Soldier.Rank.PRIVATE); // Siła: 1
        int goldBeforeAttack = 200 - 30;

        attacker.attack(defender);

        assertAll(
            () -> assertTrue(attacker.getGold()  > goldBeforeAttack, "Atakujący powinien mieć więcej złota niż przed atakiem (po rekrutacji)"),
            () -> assertTrue(defender.getGold() < 200, "Obrońca powinien stracić złoto po przegranej"),
            () -> assertEquals(2, attacker.getArmy().get(0).getExperience(), "Żołnierz atakującego powinien zyskać doświadczenie"),
            () -> assertEquals(0, defender.getArmy().get(0).getExperience(), "Żołnierz obrońcy powinien stracić doświadczenie")
        );
    }



    @Test
    void testAttackLose() {
        General attacker = new General("Alexander", 200);
        General defender = new General("Philip", 200);

        attacker.recruitSoldier(Soldier.Rank.PRIVATE); // Siła: 1
        defender.recruitSoldier(Soldier.Rank.CAPTAIN); // Siła: 3

        int defenderGoldBeforeAttack = defender.getGold(); 
        // = 170

        attacker.attack(defender);

        assertAll(
            // atakujący: pewnie < goldBeforeAttack, bo przegrał
            () -> assertTrue(attacker.getGold() < 190, 
                "Przegrany atakujący powinien mieć mniej złota niż przed atakiem"),
                
            // obrońca: > 170
            () -> assertTrue(defender.getGold() > defenderGoldBeforeAttack,
                "Wygrywający obrońca powinien mieć więcej złota niż przed atakiem"),
                
            // Doświadczenie itp.
            () -> assertEquals(2, defender.getArmy().get(0).getExperience()),
            () -> assertEquals(0, attacker.getArmy().get(0).getExperience())
        );

    }

    @Test
    void testAttackDraw() {
        // Testuje remis podczas ataku
        General general1 = new General("Alexander", 200);
        General general2 = new General("Philip", 200);

        general1.recruitSoldier(Soldier.Rank.PRIVATE); // Siła: 1
        general2.recruitSoldier(Soldier.Rank.PRIVATE); // Siła: 1

        general1.attack(general2);

        assertAll(
            // Sprawdza, czy obaj generałowie stracili po jednym żołnierzu
            () -> assertEquals(0, general1.getArmy().size(), "Obaj generałowie powinni stracić żołnierzy w przypadku remisu"),
            () -> assertEquals(0, general2.getArmy().size(), "Obaj generałowie powinni stracić żołnierzy w przypadku remisu")
        );
    }

    @Test
    void testSaveAndLoadFromFile() throws IOException {
        General general = new General("Alexander", 300);
        general.recruitSoldier(Soldier.Rank.CAPTAIN);

        String filePath = "test_general.json";
        general.saveToFile(filePath);

        General loadedGeneral = General.loadFromFile(filePath);

        assertAll(
            () -> assertEquals(general.getName(), loadedGeneral.getName(), "Nazwa generała powinna być taka sama po odczycie"),
            () -> assertEquals(general.getGold(), loadedGeneral.getGold(), "Złoto generała powinno być takie samo po odczycie"),
            () -> assertEquals(general.getArmy().size(), loadedGeneral.getArmy().size(), "Rozmiar armii powinien być taki sam po odczycie")
        );

        new File(filePath).delete(); // Usuń plik testowy
    }



    @Test
    void testSaveToCSV() throws IOException {
        // Testuje zapis generała do pliku CSV
        General general = new General("Alexander", 300);
        general.recruitSoldier(Soldier.Rank.CAPTAIN);

        String filePath = "test_general.csv";
        general.saveToCSV(filePath);

        File csvFile = new File(filePath);

        assertAll(
            // Sprawdza, czy plik CSV istnieje
            () -> assertTrue(csvFile.exists(), "Plik CSV powinien istnieć"),
            // Sprawdza, czy plik CSV nie jest pusty
            () -> assertTrue(csvFile.length() > 0, "Plik CSV nie powinien być pusty")
        );
        // Usuwa plik testowy
        csvFile.delete();
    }
}
