package com.example.debtbuddies;

public class Card {
    String suit;
    int value;
    String cardId;
    public void createCard(String s, int v, String id) {
        suit = s;
        value = v;
        cardId = id;
    }
}
