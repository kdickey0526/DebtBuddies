package com.example.debtbuddies;

/**
 * Generic representation of a card object.
 * Each card has a suit, value, and ID.
 */
public class Card {
    String suit;
    int value;
    String cardId;
    public void createCard(String s, int v, String id) {
        suit = s;
        value = v;
        cardId = id;
    }
    public String getID() {
        return cardId;
    }
}
