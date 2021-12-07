package belote;

import game.Action;

import java.util.Objects;

public class Card implements Action {
    private Suit suit;
    private CardType type;

    private boolean isTrump;
    private int value;

    public Card(Suit suit, CardType type) {
        this.suit = suit;
        this.type = type;
        this.value = type.noTrumpValue;
    }

    public Card(Suit suit, CardType type, boolean isTrump) {
        this.suit = suit;
        this.type = type;
        this.isTrump = isTrump;
        this.value = isTrump ? type.trumpValue : type.noTrumpValue;
    }

    public Card(Card card1){
        this(card1.suit, card1.type, card1.isTrump);
    }

    @Override
    public String toString() {
        return type + " of " + suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public CardType getType() {
        return type;
    }

    public void setTrump() {
        isTrump = true;
        value = type.trumpValue;
    }

    public boolean isTrump() {
        return isTrump;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return getSuit() == card.getSuit() && getType() == card.getType() && getValue() == card.getValue() && isTrump() == card.isTrump();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSuit(), getType());
    }
}
