package edu.up.sushigogamestate;

/**
 * SushiGoGameState
 *
 *
 * @author Josephine, Maria, Tony, Kenzie
 */
public class GameState {

    // ----- Instance Variables -----
    private int numPlayers;
    private int currentPlayerId;
    private boolean gameOver;

    private String[][] playerHands;     // Each player's current hand of cards
    private String[][] playerPlates;    // Cards each player has played
    private int[] playerScores;         // Each player's current score

    // ----- Constructor -----
    /**
     * Initializes the game state to the starting configuration.
     */
    public GameState() {
        numPlayers = 4;
        currentPlayerId = 0;
        gameOver = false;

        playerHands = new String[numPlayers][5];    // Each player starts with 5 cards
        playerPlates = new String[numPlayers][10];  // Maximum of 10 cards on plate
        playerScores = new int[numPlayers];

        // Populate hands with example cards
        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < 5; j++) {
                playerHands[i][j] = "Card" + (j + 1);
            }
            playerScores[i] = 0;
        }
    }

    // ----- toString() -----
    /**
     *
     * @return a string representation of the current game state.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Sushi Go Game State ===\n");
        sb.append("Number of Players: ").append(numPlayers).append("\n");
        sb.append("Current Player ID: ").append(currentPlayerId).append("\n");
        sb.append("Game Over: ").append(gameOver).append("\n");

        sb.append("\n-- Player Hands --\n");
        for (int i = 0; i < numPlayers; i++) {
            sb.append("Player ").append(i).append(": ");
            for (String card : playerHands[i]) {
                sb.append(card).append(" ");
            }
            sb.append("\n");
        }

        sb.append("\n-- Player Plates --\n");
        for (int i = 0; i < numPlayers; i++) {
            sb.append("Player ").append(i).append(": ");
            for (String card : playerPlates[i]) {
                if (card != null) sb.append(card).append(" ");
            }
            sb.append("\n");
        }

        sb.append("\n-- Scores --\n");
        for (int i = 0; i < numPlayers; i++) {
            sb.append("Player ").append(i).append(": ").append(playerScores[i]).append("\n");
        }

        return sb.toString();
    }

    // ----- Action Methods -----

    /**
     * Player selects a card from their hand.
     *
     * @param playerId ID of the player selecting the card
     * @param cardIndex index of the card in the player's hand
     * @return true if the move is valid, false otherwise
     */
    public boolean selectCard(int playerId, int cardIndex) {
        if (playerId != currentPlayerId || cardIndex < 0 || cardIndex >= playerHands[playerId].length) {
            return false;
        }

        String selected = playerHands[playerId][cardIndex];
        if (selected == null) return false;

        // Move card from hand to plate
        for (int i = 0; i < playerPlates[playerId].length; i++) {
            if (playerPlates[playerId][i] == null) {
                playerPlates[playerId][i] = selected;
                playerHands[playerId][cardIndex] = null;
                break;
            }
        }
        return true;
    }

    /**
     * Player uses a Wasabi card.
     *
     * @param playerId ID of the player using Wasabi
     * @return true if successful, false otherwise
     */
    public boolean useWasabi(int playerId) {
        if (playerId != currentPlayerId) return false;

        for (String card : playerPlates[playerId]) {
            if ("Wasabi".equals(card)) {
                playerScores[playerId] += 3; // Example scoring bonus
                return true;
            }
        }
        return false;
    }

    /**
     * Player uses Chopsticks card to take an extra card.
     *
     * @param playerId ID of the player using Chopsticks
     * @return true if successful, false otherwise
     */
    public boolean useChopsticks(int playerId) {
        if (playerId != currentPlayerId) return false;

        for (String card : playerPlates[playerId]) {
            if ("Chopsticks".equals(card)) {
                playerScores[playerId] += 1; // Example bonus
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the number of players (only before the game starts).
     *
     * @param count number of players
     * @return true if valid, false otherwise
     */
    public boolean setNumPlayers(int count) {
        if (count < 2 || count > 5 || !isGameAtStart()) return false;
        numPlayers = count;
        return true;
    }

    /**
     * Player presses "Ready" to end their turn.
     *
     * @param playerId ID of the player ending their turn
     * @return true if valid, false otherwise
     */
    public boolean ready(int playerId) {
        if (playerId != currentPlayerId) return false;

        currentPlayerId = (currentPlayerId + 1) % numPlayers;
        return true;
    }

    // ----- Helper Methods -----

    /**
     * Returns true if the game is still at the start state.
     */
    private boolean isGameAtStart() {
        for (int score : playerScores) {
            if (score != 0) return false;
        }
        return true;
    }
}
