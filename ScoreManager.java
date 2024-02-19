package Pong;

import java.io.*;
import java.util.*;

public class ScoreManager {
    private int player1Score;
    private int player2Score;
    private int scoreLimit; // Score limit to win the game
    private String highScoreFile; // File path for storing high scores

    public ScoreManager(int scoreLimit, String highScoreFile) {
        this.player1Score = 0;
        this.player2Score = 0;
        this.scoreLimit = scoreLimit;
        this.highScoreFile = highScoreFile;
    }

    public void increasePlayer1Score() {
        player1Score++;
    }

    public void increasePlayer2Score() {
        player2Score++;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public boolean isGameOver() {
        return player1Score >= scoreLimit || player2Score >= scoreLimit;
    }

    public String getWinner() {
        if (player1Score >= scoreLimit) {
            return "Player 1";
        } else if (player2Score >= scoreLimit) {
            return "Player 2";
        } else {
            return null; // Game is not over yet
        }
    }

    public void saveHighScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(highScoreFile))) {
            writer.println("Player 1: " + player1Score);
            writer.println("Player 2: " + player2Score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHighScores() {
        try (Scanner scanner = new Scanner(new File(highScoreFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String playerName = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    if (playerName.equals("Player 1")) {
                        player1Score = score;
                    } else if (playerName.equals("Player 2")) {
                        player2Score = score;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
