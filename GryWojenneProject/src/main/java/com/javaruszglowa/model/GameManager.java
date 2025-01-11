//System zarządzania grą (GameManager) - kontroluje przebieg gry, zapisuje jej stan, wykonuje akcje i zapisuje logi.
package com.javaruszglowa.model;

import java.io.IOException;

public class GameManager {
    private General general1;
    private General general2;
    private Secretary secretary;

    public GameManager(General general1, General general2, Secretary secretary) {
        this.general1 = general1;
        this.general2 = general2;
        this.secretary = secretary;
    }

    public void performAction(String action, Runnable task) {
        try {
            task.run();
            secretary.logAction(action);
        } catch (Exception e) {
            secretary.logAction("Action failed: " + action + " - " + e.getMessage());
        }
    }

    public void saveGame(String filePath) throws IOException {
        general1.saveToFile(filePath + "_general1.json");
        general2.saveToFile(filePath + "_general2.json");
        secretary.saveLogs(filePath + "_logs.txt");
    }
}
