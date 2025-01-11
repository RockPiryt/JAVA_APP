package com.javaruszglowa.main;

import com.javaruszglowa.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class WarGameTest {

    private General general1;
    private General general2;
    private Secretary secretary;
    private GameManager gameManager;
    private Random random;

    @BeforeEach
    void setUp() {
        general1 = new General("General Alexander", 500);
        general2 = new General("General Philip", 500);
        secretary = new Secretary();
        gameManager = new GameManager(general1, general2, secretary);
        random = mock(Random.class);
    }

    @Test
    void testPhaseRecruitment() {
        when(random.nextInt(Soldier.Rank.values().length)).thenReturn(0); // PRIVATE

        WarGame.phaseRecruitment(1, general1, general2, gameManager, random, secretary);

        assertAll(
            () -> assertEquals(1, general1.getArmy().size(), "Generał Alexander powinien zrekrutować jednego żołnierza"),
            () -> assertEquals(1, general2.getArmy().size(), "Generał Philip powinien zrekrutować jednego żołnierza"),
            () -> assertEquals(490, general1.getGold(), "Generał Alexander powinien mieć zmniejszone złoto"),
            () -> assertEquals(490, general2.getGold(), "Generał Philip powinien mieć zmniejszone złoto"),
            () -> assertTrue(secretary.getLogs().stream().anyMatch(log -> log.getAction().contains("zrekrutował nowego żołnierza")), "Powinno być zalogowane zrekrutowanie żołnierza")
        );
    }

    @Test
    void testPhaseTraining() {
        general1.recruitSoldier(Soldier.Rank.PRIVATE);
        general2.recruitSoldier(Soldier.Rank.CORPORAL);

        when(random.nextInt(anyInt())).thenReturn(0); // Wybór pierwszego żołnierza

        WarGame.phaseTraining(2, general1, general2, gameManager, random, secretary);

        assertAll(
            () -> assertEquals(2, general1.getArmy().get(0).getExperience(), "Doświadczenie żołnierza Generała Alexandra powinno wzrosnąć"),
            () -> assertEquals(2, general2.getArmy().get(0).getExperience(), "Doświadczenie żołnierza Generała Philipa powinno wzrosnąć"),
            () -> assertTrue(secretary.getLogs().stream().anyMatch(log -> log.getAction().contains("manewry")), "Powinno być zalogowane wykonanie manewrów")
        );
    }

    @Test
    void testPhaseWarVictory() {
        general1.recruitSoldier(Soldier.Rank.CAPTAIN); // Siła: 3
        general2.recruitSoldier(Soldier.Rank.PRIVATE); // Siła: 1

        when(random.nextInt(anyInt())).thenReturn(0); // Wybór pierwszego żołnierza

        WarGame.phaseWar(3, general1, general2, gameManager, random, secretary);

        assertAll(
            () -> assertTrue(secretary.getLogs().stream().anyMatch(log -> log.getAction().contains("wygrał bitwę")), "Powinna być zalogowana wygrana bitwa"),
            () -> assertEquals(2, general1.getArmy().get(0).getExperience(), "Doświadczenie żołnierza Generała Alexandra powinno wzrosnąć"),
            () -> assertEquals(0, general2.getArmy().get(0).getExperience(), "Doświadczenie żołnierza Generała Philipa powinno spaść")
        );
    }



    @Test
    void testGameManagerSaveState() throws IOException {
        WarGame.phaseRecruitment(1, general1, general2, gameManager, random, secretary);
        WarGame.phaseTraining(1, general1, general2, gameManager, random, secretary);
        WarGame.phaseWar(1, general1, general2, gameManager, random, secretary);

        gameManager.saveGame("test_game_round_1");

        assertTrue(new java.io.File("test_game_round_1_general1.json").exists(), "Plik stanu Generała Alexandra powinien istnieć");
        assertTrue(new java.io.File("test_game_round_1_general2.json").exists(), "Plik stanu Generała Philipa powinien istnieć");

        new java.io.File("test_game_round_1_general1.json").delete();
        new java.io.File("test_game_round_1_general2.json").delete();
    }
}
