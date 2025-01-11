package com.javaruszglowa.main;

import com.javaruszglowa.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WarGame {
    public static void main(String[] args) throws IOException {
        General general1 = new General("General Alexander", 500);
        General general2 = new General("General Philip", 500);
        Secretary secretary = new Secretary();
        GameManager gameManager = new GameManager(general1, general2, secretary);
        Random random = new Random();

        // Symulacja 15 rund gry
        for (int round = 1; round <= 15; round++) {
            secretary.logAction("--- Runda " + round + " ---");
            System.out.println("Rozpoczęcie rundy " + round);

            // Wykonanie etapów gry
            phaseRecruitment(round, general1, general2, gameManager, random, secretary);
            phaseTraining(round, general1, general2, gameManager, random, secretary);
            phaseWar(round, general1, general2, gameManager, random, secretary);

            secretary.logAction("--- Koniec rundy " + round + " ---\n");
            gameManager.saveGame("game_round_" + round);
        }

        // Wyświetlenie końcowego raportu
        System.out.println("--- Końcowy raport ---");
        secretary.printLogs();
        secretary.saveLogs("final_report.txt");
    }

    // Faza rekrutacji
    public static void phaseRecruitment(int round, General general1, General general2, GameManager gameManager, Random random, Secretary secretary) {
        secretary.logAction("Faza rekrutacji w rundzie " + round);
        gameManager.performAction("Rekrutacja żołnierzy", () -> {
            general1.recruitSoldier(getRandomRank(random));
            secretary.logAction("Generał Alexander zrekrutował nowego żołnierza");

            general2.recruitSoldier(getRandomRank(random));
            secretary.logAction("Generał Philip zrekrutował nowego żołnierza");
        });
    }

    // Faza manewrów
    public static void phaseTraining(int round, General general1, General general2, GameManager gameManager, Random random, Secretary secretary) {
        secretary.logAction("Faza manewrów w rundzie " + round);
        gameManager.performAction("Manewry Generała Alexandra", () -> {
            performManeuversForDivision(general1, random);
            secretary.logAction("Generał Alexander przeprowadził manewry dla części swojej armii");
        });

        gameManager.performAction("Manewry Generała Philipa", () -> {
            performManeuversForDivision(general2, random);
            secretary.logAction("Generał Philip przeprowadził manewry dla części swojej armii");
        });
    }

    // Faza bitwy
    // Faza bitwy
public static void phaseWar(int round, General general1, General general2, GameManager gameManager, Random random, Secretary secretary) {
    secretary.logAction("Faza bitwy w rundzie " + round);
    gameManager.performAction("Bitwa między generałami", () -> {
        List<Soldier> division1 = getDivision(general1, random);
        List<Soldier> division2 = getDivision(general2, random);

        int strength1 = calculateStrength(division1);
        int strength2 = calculateStrength(division2);

        if (strength1 > strength2) {
            handleVictory(general1, general2, division1, division2, secretary, strength1, strength2);
        } else if (strength1 < strength2) {
            handleVictory(general2, general1, division2, division1, secretary, strength2, strength1);
        } else {
            handleDraw(division1, division2, secretary);
        }
    });
}


    private static Soldier.Rank getRandomRank(Random random) {
        Soldier.Rank[] ranks = Soldier.Rank.values();
        return ranks[random.nextInt(ranks.length)];
    }

    private static int calculateStrength(List<Soldier> division) {
        return division.stream().mapToInt(Soldier::getStrength).sum();
    }

    private static List<Soldier> getDivision(General general, Random random) {
        List<Soldier> army = general.getArmy();
        int divisionSize = Math.max(1, random.nextInt(Math.max(army.size(), 1)));
        return new ArrayList<>(army.subList(0, divisionSize));
    }

    private static void removeRandomSoldier(List<Soldier> division, Random random) {
        if (!division.isEmpty()) {
            division.remove(random.nextInt(division.size()));
        }
    }

    private static void performManeuversForDivision(General general, Random random) {
        List<Soldier> division = getDivision(general, random);
        division.forEach(soldier -> {
            int cost = soldier.getRank().getValue();
            if (general.getGold() >= cost) {
                general.removeGold(cost);
                soldier.gainExperience();
            }
        });
    }

    private static void handleVictory(General winner, General loser, List<Soldier> winningDivision, List<Soldier> losingDivision, Secretary secretary, int winningStrength, int losingStrength) {
        int loot = (int) (loser.getGold() * 0.1);
        winner.addGold(loot);
        loser.removeGold(loot);
        winningDivision.forEach(Soldier::gainExperience);
        losingDivision.forEach(Soldier::loseExperience);
        secretary.logAction("Generał " + winner.getName() + " wygrał bitwę z siłą: " + winningStrength + " przeciwko " + losingStrength);
    }

    private static void handleDraw(List<Soldier> division1, List<Soldier> division2, Secretary secretary) {
        removeRandomSoldier(division1, new Random());
        removeRandomSoldier(division2, new Random());
        secretary.logAction("Bitwa zakończyła się remisem. Obie strony straciły po jednym żołnierzu.");
    }
}
