package belote;

public enum CardType {
    SEVEN(0, 0, 0, 0),
    EIGHT(1, 1, 0, 0),
    NINE(6, 2, 14, 0),
    TEN(4, 3, 10, 10),
    JACK(7, 4, 20, 2),
    QUEEN(2, 5, 3, 3),
    KING(3, 6, 4, 4),
    ACE(5, 7, 11, 11);

    public int trumpOrder;
    public int noTrumpOrder;
    public int trumpValue;
    public int noTrumpValue;

    CardType(int trumpOrder, int noTrumpOrder, int trumpValue, int noTrumpValue) {
        this.trumpOrder = trumpOrder;
        this.noTrumpOrder = noTrumpOrder;
        this.trumpValue = trumpValue;
        this.noTrumpValue = noTrumpValue;
    }
}
