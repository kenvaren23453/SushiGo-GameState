package edu.up.sushigogamestate;

import java.util.ArrayList;

/**
 * The current state of a Sushi Go game.
 */

public class SushiGameState {

    public static final String[] CARD_NAMES = {
            "UNKNOWN",  //used to hide other player's cards
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


    private int whoseTurn = 0;
    private ArrayList< ArrayList<String> > hands = new ArrayList<>();

    /**
     * init ctor that creates the initial game state
     */
    public SushiGameState(int numPlayers) {
        //TODO
    }

    /**
     * copy ctor that creates a copy of a given game state from the perspective
     * of a given player
     */
    public SushiGameState(SushiGameState other, int pid) {
        this.whoseTurn = other.whoseTurn;

        //Make a deep copy of the hands 2D arraylist
        this.hands =  new ArrayList<>();
        for(ArrayList<String> otherHand : other.hands) {
            //TODO:  this is a complete copy, we only want to copy the card that
            //are pid's hand.  Replace other players' card with "UNKNOWN"

            ArrayList<String> myHandCopy = new ArrayList<String>();
            hand.addAll(otherHand);
            myHandCopy.add(hand);
        }
    }

    /** Does the given player have the given card in their hand? */
    public boolean contains(int playerId, String card) {
        //TODO
        return false;
    }

}
