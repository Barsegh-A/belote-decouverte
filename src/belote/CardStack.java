package belote;

import java.util.Objects;

public class CardStack {
    private Card topCard;
//    private boolean hasCardBelow;
    private Card bottomCard;


    public CardStack(Card topCard, boolean hasCardBelow) {
        this.topCard = topCard;
//        this.hasCardBelow = hasCardBelow;
    }

    public CardStack(Card topCard, Card bottomCard) {
        this.topCard = topCard;
        this.bottomCard = bottomCard;
//        this.hasCardBelow = bottomCard == null ? false : true;
    }

    public CardStack(CardStack cardStack){
        // reconsider this approach
        Card topCard = cardStack.topCard == null ? null : new Card(cardStack.topCard);
        Card belowCard = cardStack.bottomCard == null ? null : new Card(cardStack.bottomCard);

        this.topCard = topCard;
        this.bottomCard = belowCard;
//        this.hasCardBelow = cardStack.hasCardBelow;
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

//        hasCardBelow = false;

        return topCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardStack)) return false;
        CardStack cardStack = (CardStack) o;
        // consider removing the equality check for belowCard
        boolean topCardsEquality = Objects.equals(topCard, cardStack.topCard);
        boolean bottomCardsEquality = Objects.equals(bottomCard, cardStack.bottomCard);
        return /*hasCardBelow == cardStack.hasCardBelow &&*/ topCardsEquality && bottomCardsEquality;
    }

    @Override
    public int hashCode() {
        // FIXME use appropriate hashcode
//        return Objects.hash(topCard, bottomCard);
        return 0;//Objects.hash(topCard, hasCardBelow, bottomCard);
    }

    @Override
    public String toString() {
        return "\nCardStack{" +
                "\nTop card=" + topCard +
                "\nBottom card=" + bottomCard +
                '}';
    }



    public static void main(String[] args) {
        // Case 1 Both Card Stack contain 2 cards
        CardStack cs1 = new CardStack(new Card(Suit.SPADES, CardType.JACK), new Card(Suit.SPADES, CardType.QUEEN));
        CardStack cs2 = new CardStack(new Card(Suit.SPADES, CardType.JACK), new Card(Suit.DIAMONDS, CardType.QUEEN));

//        System.out.println(cs1.hashCode());
//        System.out.println(cs2.hashCode());
//        System.out.println(cs1.equals(cs2));

        // Case 2: 1 && 2

        cs1 = new CardStack(new Card(Suit.SPADES, CardType.JACK), new Card(Suit.DIAMONDS, CardType.QUEEN));
        cs2 = new CardStack(new Card(Suit.SPADES, CardType.JACK), null);

//        System.out.println(cs1.hashCode());
//        System.out.println(cs2.hashCode());
//        System.out.println(cs1.equals(cs2));

        // Case 3: 1 & 1

        cs1 = new CardStack(new Card(Suit.HEARTS, CardType.JACK), null);
        cs2 = new CardStack(new Card(Suit.HEARTS, CardType.KING), null);

//        System.out.println(cs1.hashCode());
//        System.out.println(cs2.hashCode());
//        System.out.println(cs1.equals(cs2));

        // Case 4: 1 & 0

        cs1 = new CardStack(new Card(Suit.HEARTS, CardType.JACK), null);
        cs2 = new CardStack(null, null);

//        System.out.println(cs1.hashCode());
//        System.out.println(cs2.hashCode());
//        System.out.println(cs1.equals(cs2));

        // Case 5 0 & 0

        cs1 = new CardStack(null, null);
        cs2 = new CardStack(null, null);

//        System.out.println(cs1.hashCode());
//        System.out.println(cs2.hashCode());
//        System.out.println(cs1.equals(cs2));


    }
}


/*

CardStack{
Top card=EIGHT of SPADES
Bottom card=EIGHT of DIAMONDS},
CardStack{
Top card=KING of SPADES
Bottom card=NINE of HEARTS},
CardStack{
Top card=JACK of DIAMONDS
Bottom card=JACK of SPADES},
CardStack{
Top card=EIGHT of HEARTS
Bottom card=TEN of HEARTS},
CardStack{
Top card=ACE of SPADES
Bottom card=SEVEN of HEARTS},
CardStack{
Top card=KING of HEARTS
Bottom card=TEN of CLUBS},
CardStack{
Top card=JACK of HEARTS
Bottom card=TEN of SPADES},
CardStack{
Top card=ACE of DIAMONDS
Bottom card=TEN of DIAMONDS}]



CardStack{
Top card=ACE of HEARTS
Bottom card=SEVEN of DIAMONDS},
CardStack{
Top card=ACE of CLUBS
Bottom card=JACK of CLUBS},
CardStack{
Top card=QUEEN of HEARTS
Bottom card=QUEEN of DIAMONDS},
CardStack{
Top card=SEVEN of CLUBS
Bottom card=KING of CLUBS},
CardStack{
Top card=NINE of DIAMONDS
Bottom card=QUEEN of SPADES},
CardStack{
Top card=EIGHT of CLUBS
Bottom card=SEVEN of SPADES},
CardStack{
Top card=NINE of CLUBS
Bottom card=NINE of SPADES},
CardStack{
Top card=KING of DIAMONDS
Bottom card=QUEEN of CLUBS}

 */