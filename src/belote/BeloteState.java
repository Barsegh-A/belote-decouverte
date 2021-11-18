package belote;

import game.Action;
import game.Player;
import game.State;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BeloteState implements State {

    public static final Suit TRUMP = Suit.DIAMONDS;
    public static final CardComparator COMPARATOR = new CardComparator();

    private final Player player;

    private final Set<CardStack> maxHand;
    private final Set<CardStack> minHand;
    private final Set<Card> unknownCards;
    private final Trick trick;
    private final int score;


    public BeloteState(Player player, Set<CardStack> maxHand, Set<CardStack> minHand, Set<Card> unknownCards, Trick trick, int score) {
        this.player = player;
        this.maxHand = maxHand;
        this.minHand = minHand;
        this.unknownCards = unknownCards;
        this.trick = trick;
        this.score = score;
    }

    public BeloteState(BeloteState state){
        this.player = state.player;
        this.trick = new Trick(state.trick);
        this.score = state.score;

        this.unknownCards = state.unknownCards.stream().map(card -> new Card(card)).collect(Collectors.toSet());
        this.maxHand = state.maxHand.stream().map(cardStack -> new CardStack(cardStack)).collect(Collectors.toSet());
        this.minHand = state.minHand.stream().map(cardStack -> new CardStack(cardStack)).collect(Collectors.toSet());
    }


    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Set<Action> getApplicableActions() {
        // Leading player can play whichever card he prefers
        if(trick.isEmpty()){
            return  getCards(player);
        }

        // 2nd player should play a card of the same suit if possible
        Suit currentTrickSuit = trick.getFirstCard().getSuit();

        // In case of trumps he should play a higher order trumps if possible
        if(currentTrickSuit == TRUMP){
            CardType trickCard = trick.getFirstCard().getType();
            Set<Action> trumpCards = getCardsWithGivenSuit(player, TRUMP);
            if(!trumpCards.isEmpty()) {
                Set<Action> higherTrumpCards =  trumpCards.stream().filter(action -> {
                    Card card = (Card) action;
                    return card.getType().trumpOrder > trickCard.trumpOrder;
                }).collect(Collectors.toSet());

                // if not he should play any trump he has
                if(!higherTrumpCards.isEmpty()){
                    return higherTrumpCards;
                }
                return trumpCards;
            }
        }

        // for other suits as well suit must be followed by the second player
        Set<Action> currentTrickSuitCards = getCardsWithGivenSuit(player, currentTrickSuit);
        if(!currentTrickSuitCards.isEmpty()) {
            return currentTrickSuitCards;
        }

        // if not, he should play a trump card
        Set<Action> trumpCards = getCardsWithGivenSuit(player, TRUMP);
        if(!trumpCards.isEmpty()) {
            return trumpCards;
        }

        // otherwise, he can play whatever card he prefers
        return getCards(player);
    }

    @Override
    public State getActionResult(Action action) {
        Trick newTrick = new Trick(trick);
        Set<Card> newUnknownCards = unknownCards.stream().map(card -> new Card(card)).collect(Collectors.toSet());
        Set<CardStack> newMaxHand = maxHand.stream().map(cardStack -> new CardStack(cardStack)).collect(Collectors.toSet());
        Set<CardStack> newMinHand = minHand.stream().map(cardStack -> new CardStack(cardStack)).collect(Collectors.toSet());

        Set<CardStack> currentPlayerHand = player == Player.MAX ? newMaxHand : newMinHand;

        CardStack cs = currentPlayerHand.stream().filter(cardStack -> cardStack.getCard().equals(action)).collect(Collectors.toList()).get(0);
        Card newCard = cs.takeCard();
        newUnknownCards.remove(newCard);

        if(newTrick.isEmpty()){
            newTrick.play(player, (Card) action);
            if(player == Player.MAX){
                return new BeloteState(Player.MIN, newMaxHand, newMinHand, newUnknownCards, newTrick, score);
            } else {
                return new BeloteState(Player.MAX, newMaxHand, newMinHand, newUnknownCards, newTrick, score);
            }
        } else {
            newTrick.play(player, (Card) action);
            Player lead = newTrick.getWinner();
            int newScore = lead == Player.MAX ? score + newTrick.getScore() : score;

            return new BeloteState(lead, newMaxHand, newMinHand, unknownCards, new Trick(lead, false), newScore);
        }
    }

    private Set<CardStack> getHand(Player player){
        Set<CardStack> hand = player == Player.MAX ? maxHand : minHand;
        return hand;
    }

    private Set<Action> getCards(Player player){
        return getHand(player).stream().map((cardStack -> cardStack.getCard())).filter((card -> card != null)).collect(Collectors.toSet());
    }

    private Set<Action> getCardsWithGivenSuit(Player p, Suit suit){
        Set<Action> cards = getCards(p);

        return cards.stream().filter(action -> {
            Card card = (Card) action;
            return card.getSuit() == suit;
        }).collect(Collectors.toSet());

    }

    public int getScore(){
        return score;
    }

    public static void main(String[] args) {
        CardStack cs1 = new CardStack(new Card(Suit.DIAMONDS, CardType.ACE), true);
        CardStack cs2 = new CardStack(new Card(Suit.CLUBS, CardType.KING), false);
        CardStack cs3 = new CardStack(null, true);
        CardStack cs4 = new CardStack(new Card(Suit.CLUBS, CardType.SEVEN), false);

        Set<Card> cards = new HashSet<>();
        Card c1 = new Card(Suit.DIAMONDS, CardType.ACE);
        cards.add(c1);
        cards.add(new Card(Suit.SPADES, CardType.ACE));

        Set<CardStack> s = new HashSet<>();
        s.add(cs1);
        s.add(cs2);
        s.add(cs3);
        s.add(cs4);

        BeloteState bs = new BeloteState(Player.MAX, s, s, cards, new Trick(Player.MAX, false), 0);

        BeloteState bs2 = new BeloteState(bs);

        System.out.println(bs.unknownCards);
        System.out.println(bs2.unknownCards);

        c1.setTrump();
        bs2.unknownCards.forEach(card -> System.out.println(card.isTrump()));
//        bs.getMaxCards().forEach((card -> System.out.println(card)));

//        bs.getCardsWithGivenSuit(Player.MAX, Suit.SPADES).forEach((card -> System.out.println(card)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeloteState)) return false;
        BeloteState that = (BeloteState) o;
        return score == that.score && getPlayer() == that.getPlayer() && maxHand.equals(that.maxHand) && minHand.equals(that.minHand) && unknownCards.equals(that.unknownCards) && trick.equals(that.trick);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayer(), maxHand, minHand, unknownCards, trick, score);
    }

    @Override
    public String toString() {
        return "BeloteState{" +
                "player=" + player +
                ", maxHand=" + maxHand +
                ", minHand=" + minHand +
                ", unknownCards=" + unknownCards +
                ", trick=" + trick +
                ", score=" + score +
                '}';
    }
}
