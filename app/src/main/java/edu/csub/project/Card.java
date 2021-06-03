package edu.csub.project;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Class to create and control Card objects to contain and represent the data of an individual card.
 */
public class Card implements Serializable {
    private String name, mana, oracle, type, power, tough, sort;
    private String[] subtypes;
    private int cmc, quant;

    /**
     * Default constructor
     */
    public Card(){

    };

    /**
     * Simple constructor
     * @param name Card Name
     * @param mana Mana Cost
     * @param type Card Type
     * @param quant Quantity of card in deck
     */
    public Card(String name, String mana, String type, int quant){
        this.name = name;
        this.mana = mana;
        this.type = type;
        this.quant = quant;
    }

    /**
     * Full constructor
     * @param name Card Name
     * @param mana Mana Cost
     * @param oracle Card Text
     * @param type Card Type
     * @param power Card Power
     * @param tough Card Toughness
     * @param cmc Converted Mana Cost
     * @param quant Quantity of card in deck
     * @param subtypes Card subtypes
     */
    public Card(String name, String mana, String oracle, String type,
                String power, String tough, int cmc, int quant, String[] subtypes){
        this.name = name;
        this.mana = mana;
        this.cmc = cmc;
        this.oracle = oracle;
        this.type = type;
        this.power = power;
        this.tough = tough;
        this.quant = quant;
        this.subtypes = subtypes;
    }

    /**
     * Getters and setters
     */
    public String getName() {
        return name;
    }

    public String getMana() {
        return mana;
    }


    public String getOracle() {
        return oracle;
    }

    public String getType() {
        return type;
    }

    public int getCmc() {
        return cmc;
    }

    public String getPower() {
        return power;
    }

    public String  getTough() {
        return tough;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public void setSort(String sort){
        this.sort = sort;
    }

    public String getSort(){
        return this.sort;
    }

    public String getSubtypes() {
        String temp = "";
        for(String str: subtypes){
            temp += (str + " ") ;
        }
        return temp;
    }

    public void setSubtypes(String[] subtypes) {
        this.subtypes = subtypes;
    }

    /**
     * Sets values after card creation
     * @param name Card Name
     * @param mana Mana Cost
     * @param oracle Card Text
     * @param type Card Type
     * @param power Card Power
     * @param tough Card Toughness
     * @param cmc Converted Mana Cost
     * @param quant Quantity of card in deck
     * @param subtypes Card subtypes
     */
    public void setAll(String name, String mana, String oracle, String type,
                       String power, String tough, int cmc, int quant){
        this.name = name;
        this.mana = mana;
        this.cmc = cmc;
        this.oracle = oracle;
        this.type = type;
        this.power = power;
        this.tough = tough;
        this.quant = quant;
    }


    /**
     * Overriden toString to output card attributes
     * @return Card in string format
     */
    @NonNull
    @Override
    public String toString(){
        return String.format("%s: %s - %s - %s", this.quant, this.name, this.type, this.mana);
    }
}
