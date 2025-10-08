package edu.up.sushigogamestate;

import java.util.ArrayList;

/**
 * SushiGoGameState
 *
 *
 * @author Josephine, Maria, Tony, Kenzie
 */
public class GameState {

    // ----- Instance Variables -----

    //deck of all cards
    private ArrayList<String> deck = new ArrayList<>();

    public static final String[] CARD_NAMES = {
            "UNKNOWN",  //used to hide other player's cards
            "EMPTY", //used to indicate an empty card slot in a players hand so it wont be null
            "Sashimi",
            "Tempura",
            "Sashimi",
            "Dumpling",
            "Maki 1",
            "Maki 2",
            "Maki 3",
            "Salmon Nigiri",
            "Egg Nigiri",
            "Squid Nigiri",
            "Pudding",
            "Wasabi",
            "Chopsticks"};

    private int numPlayers;

    //private int currentPlayerId;

    //array of booleans which says which players have chosen a card/are ready
    private boolean[] readyPlayers;

    private boolean gameOver;

    private String[][] playerHands;     // Each player's current hand of cards

    private String[][] playerPlates;    // Cards each player has played

    private int[] playerScores;         // Each player's current score


    //Helper method to populate deck (doesn't shuffle it yet)
    public void populateDeck() {
        //TO DO
    }

    // ----- Constructor -----

    /**
     * Default constructor (takes no args, passes in default args to main constructor)
     */
    public GameState() {
        new GameState(2);
    }


    /**
     * Initializes the game state to the starting configuration.
     */
    public GameState(int numGamers) {
        numPlayers = numGamers;
        //currentPlayerId = 0;
        gameOver = false;

        //if incorrect num players arg is passed in, run game with default 2 players:
        if(numPlayers > 5 || numPlayers < 2) {
            numPlayers = 2;
        }

        int handSize = 0;
        //determine number of cards in each hand based on number of players
        //5 players = 7 cards, 4 = 8 cards, 3 = 9 cards, 2 = 10 cards
        switch(numPlayers) {
            case 2:
                handSize = 10;
                break;
            case 3: handSize = 9;
                break;

            case 4:
                handSize = 8;
                break;
            case 5:
                handSize = 7;
                break;
        }

        playerHands = new String[numPlayers][handSize];    // initialize player hands array holding max cards (hand size)
        playerPlates = new String[numPlayers][handSize];  // up to the starting hand size card amount will end up played
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
