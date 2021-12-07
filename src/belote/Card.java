package belote;

import game.Action;

import java.util.Objects;

public class Card implements Action {
    private Suit suit;
    private CardType type;

    private boolean isTrump;
    private int value;

    // TODO handle the case of trumps suit
    public Card(Suit suit, CardType type) {
        this.suit = suit;
        this.type = type;
        this.value = type.noTrumpValue;
    }

    public Card(Card card1){
        this(card1.suit, card1.type);
        this.isTrump = card1.isTrump;
        this.value = card1.value;
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
