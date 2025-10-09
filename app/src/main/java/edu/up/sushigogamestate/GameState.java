package edu.up.sushigogamestate;

import java.util.ArrayList;
import java.util.Arrays;

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

    private int currentPlayerId;
    //could also be named actingPlayerId, thisPlayerId if worried about confusion since there are no turns

    //array of booleans which says which players have chosen a card/are ready
    private boolean[] readyPlayers;

    private boolean gameOver;

    private String[][] playerHands;     // Each player's current hand of cards

    private String[][] playerPlates;    // Cards each player has played

    private int[] playerScores;         // Each player's current score


    /**
     * Helper method to populate deck (doesn't shuffle it yet)
     *
     * There are 108 cards in a sushi go deck
     * each card type is added to the deck the amount of times there are cards of that type in the deck
     *
     */
    public void populateDeck(ArrayList<String> deck) {

        //I definitely should have made a helper method to do this but oh well
        for(int i = 0; i < 14; i ++) {
            //there are 14 of each of the following card types in the deck. this loop adds them.
            deck.add(CARD_NAMES[3]); //Tempura
            deck.add(CARD_NAMES[4]); //Sashimi
            deck.add(CARD_NAMES[5]); //Dumpling

            //there are 12 double maki cards in the deck:
            if(i < 12) {
                deck.add(CARD_NAMES[7]); //Maki Roll 2

                //there are 10 of these cards in the deck:
                if(i < 10) {

                    deck.add(CARD_NAMES[9]); //Salmon Nigiri
                    deck.add(CARD_NAMES[12]); //Pudding

                    //there are 8 triple maki cards in the deck:
                    if (i < 8) {
                        deck.add(CARD_NAMES[8]); // Maki Roll 3

                        //there are 6 of these cards in the deck:
                        if (i < 6) {
                            deck.add(CARD_NAMES[13]); //Wasabi
                            deck.add(CARD_NAMES[6]); //Maki Roll 1

                            //there are 5 of these cards in the deck:
                            if (i < 5) {
                                deck.add(CARD_NAMES[11]); //Squid Nigiri
                                deck.add(CARD_NAMES[10]); //Egg Nigiri

                                //there are 4 chopsticks cards in the deck:
                                if (i < 4) {
                                    deck.add(CARD_NAMES[14]); //Chopsticks
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    /**
     * Shuffle Deck
     *
     * Reassigns each card in the given deck to a random location
     */
    public void shuffleDeck(ArrayList<String> deck) {
        //TODO josie
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

        //TODO copy deck how its currently shuffled??
    }

    /**
     * Copy constructor
     * Param: gamestate object
     * makes a deep copy of the given gamestate in the pov of the given player
     */
    public GameState(GameState other, int player) {
        GameState copyState = new GameState(other.numPlayers);

        //copy simple instance variables (ints, bools, 1d arrays
        copyState.currentPlayerId = other.currentPlayerId;
        copyState.gameOver = other.gameOver;
        copyState.readyPlayers = Arrays.copyOf(other.readyPlayers, other.readyPlayers.length);
        copyState.playerHands = Arrays.copyOf(other.playerHands, other.playerHands.length);

        //make other players hands unknown but keep this player's hand known to themself
        for(int i = 0; i < copyState.playerHands.length; i ++) {
            if (i != player) { //if opponent
                for(int j = 0; j < copyState.playerHands[i].length; j ++) {
                    if (!(copyState.playerHands[i][j].equals(CARD_NAMES[1]))) { //EMPTY
                        //if its not an empty space in opponents hand, change it to unknown card to conceal their hand
                        copyState.playerHands[i][j] = CARD_NAMES[0]; //UNKNOWN
                    }
                }
            }
        }

        //copy all player plates. these are visible to everyone
        for(int i = 0; i < other.numPlayers; i ++) {
            copyState.playerPlates[i] = Arrays.copyOf(other.playerPlates[i], other.playerPlates[i].length);
        }

        //copy player scores. these are visible to everyone? because you could count anyones score by looking at their plate.
        copyState.playerScores = Arrays.copyOf(other.playerScores, other.numPlayers);

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
