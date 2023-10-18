package onetoone.TexasHoldEm;

public enum PokerHands {
    ROYAL_FLUSH(23),
    STRAIGHT_FLUSH(22),
    FOUR_OF_A_KIND(21),
    FULL_HOUSE(20),
    FLUSH(19),
    STRAIGHT(18),
    THREE_OF_A_KIND(17),
    TWO_PAIR(16),
    PAIR(15),
    HIGH_ACE(14),
    HIGH_KING(13),
    HIGH_QUEEN(12),
    HIGH_JACK(11),
    HIGH_TEN(10),
    HIGH_NINE(9),
    HIGH_EIGHT(8),
    HIGH_SEVEN(7),
    HIGH_SIX(6),
    HIGH_FIVE(5),
    HIGH_FOUR(4),
    HIGH_THREE(3),
    HIGH_TWO(2),
    LOW(1);

    private final int value;

    PokerHands(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
