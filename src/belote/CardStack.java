package belote;

public class CardStack {
    private Card topCard;
    private boolean hasCardBelow;


    private Card belowCard;


    public CardStack(Card topCard, boolean hasCardBelow) {
        this.topCard = topCard;
        this.hasCardBelow = hasCardBelow;
    }

    public Card getCard(){
        return topCard;
    }

    public Card takeCard(){
//        Card card = topCard;

        topCard = belowCard;
        belowCard = null;

        hasCardBelow = false;

        return topCard;
    }
}
