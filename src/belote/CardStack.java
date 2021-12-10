package belote;

import java.util.Objects;

public class CardStack {
    private Card topCard;
    private Card bottomCard;

    public CardStack(Card topCard, Card bottomCard) {
        this.topCard = topCard;
        this.bottomCard = bottomCard;
    }

    public CardStack(CardStack cardStack){
        Card topCard = cardStack.topCard == null ? null : new Card(cardStack.topCard);
        Card belowCard = cardStack.bottomCard == null ? null : new Card(cardStack.bottomCard);

        this.topCard = topCard;
        this.bottomCard = belowCard;
    }

    public Card getCard(){
        return topCard;
    }

    public boolean isEmpty(){
        return topCard == null;
    }

    // returns the newly opened card
    public Card takeCard(){

        topCard = bottomCard;
        bottomCard = null;

        return topCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardStack)) return false;
        CardStack cardStack = (CardStack) o;

        boolean topCardsEquality = Objects.equals(topCard, cardStack.topCard);
        boolean bottomCardsEquality = Objects.equals(bottomCard, cardStack.bottomCard);

        return topCardsEquality && bottomCardsEquality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(topCard, bottomCard);
    }

    @Override
    public String toString() {
        return "\nCardStack{" +
                "\nTop card=" + topCard +
                "\nBottom card=" + bottomCard +
                '}';
    }
}