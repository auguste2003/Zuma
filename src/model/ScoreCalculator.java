package model;

public class ScoreCalculator implements Runnable {
    private volatile boolean running = true;
    private int score = 0;
    private long startTime;
    private long elapsedTime;

    public ScoreCalculator() {
        // Startzeit speichern, wenn der ScoreCalculator erstellt wird
        startTime = System.currentTimeMillis();
    }

    // Methode, die aufgerufen wird, wenn ein Ball entfernt wird
    public synchronized void ballRemoved() {
        score += 10; // Füge 10 Punkte hinzu, wenn ein Ball entfernt wird
    }

    // Methode zum Abrufen des aktuellen Punktestands
    public synchronized int getScore() {
        return score;
    }

    // Methode zum Abrufen der vergangenen Spielzeit in Sekunden
    public synchronized long getElapsedTime() {
        return elapsedTime / 1000; // Umrechnung von Millisekunden in Sekunden
    }

    // Methode zum Anhalten des Threads
    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            // Aktualisiere die vergangene Zeit
            elapsedTime = System.currentTimeMillis() - startTime;

            try {
                // Warten, um die CPU nicht zu überlasten
                Thread.sleep(100);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }
}
