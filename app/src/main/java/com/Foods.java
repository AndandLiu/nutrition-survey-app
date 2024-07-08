package com.jla388.sfu.greenfoodchallenge;

import java.io.Serializable;

/**
 * Class that represents a Food item
 * such as beef and C02e per kilogram of meat it produces.
 */
public class Foods implements Serializable {
    private String name;
    private double amount_input=0;
    private double amount_suggest;
    private double CO2e_actual=0;
    private double CO2ePerKilo;
    private boolean is_selected=false;


    //Every function down here is a getter or a setter
    /**
     * Constructor for the Food class
     * @param name - name of the food
     * @param amount_suggest - suggested amount of that food
     * @param CO2ePerKilo - number of C02e per 1 kg of that particular food
     */
    public Foods(String name, double amount_suggest, double CO2ePerKilo) {
        this.name = name;
        this.amount_suggest = amount_suggest;
        this.CO2ePerKilo = CO2ePerKilo;
    }

    /**
     * Setter function
     * @param is_selected
     */
    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    /**
     * Getter function
     * @return true if is_selected
     */
    public boolean getIs_selected(){return this.is_selected;}

    /**
     * Getter function
     * @return the name of the food
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method
     * @param name - name of the food
     */
    public void setName(String name) {
        this.name = name;
    }

    public double getAmount_input() {
        return amount_input;
    }

    public void setAmount_input(double amount_input) {
        this.amount_input = amount_input;
    }

    public double getAmount_suggest() {
        return amount_suggest;
    }

    public void setAmount_suggest(double amount_suggest) {
        this.amount_suggest = amount_suggest;
    }

    public double getCO2e_actual() {
        return CO2e_actual;
    }

    public void setCO2e_actual(double CO2e_actual) {
        this.CO2e_actual = CO2e_actual;
    }

    public double getCO2ePerKilo() {
        return CO2ePerKilo;
    }

    public void setCO2ePerKilo(double CO2e_suggest) {
        this.CO2ePerKilo = CO2e_suggest;
    }
}
