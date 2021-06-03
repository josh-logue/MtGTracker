package edu.csub.project;

import java.util.ArrayList;

/**
 * Class for creating an managing a collection of decks and cards
 */
public final class DeckList {
    private static final DeckList INSTANCE = new DeckList();
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private String deckName;
    private String owner;

    /**
     * Default constructor
     */
    private DeckList(){

    }

    /**
     * Gets an instance of the DeckList
     * @return DeckList instance
     */
    public static DeckList getInstance(){
        return INSTANCE;
    }

    /**
     * Getters and setters
     */
    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }


    public void setList(ArrayList<Card> cards){
        this.cards = cards;
    }

    public ArrayList<Card> getList(){
        return this.cards;
    }

    public void setNames(ArrayList<String> names){
        this.names = names;
    }

    public void setIds(ArrayList<String> ids){
        this.ids = ids;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = String.format("%s's Decks", owner);
    }

    /**
     * Clears the cards in the list
     */
    public void clearCards() { cards.clear(); }

    /**
     * Overriden toString to print just the current deck name
     * @return deck name
     */
    @Override
    public String toString(){
        return String.format("%s", this.deckName);
    }
}
