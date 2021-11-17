package belote;

import game.Action;
import game.Player;
import game.State;

import java.util.HashSet;
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
        if(trick.isEmpty()){
            //clone trick
            trick.play(player, (Card) action);
            CardStack cs = getHand(player).stream().filter(cardStack -> cardStack.getCard().equals(action)).collect(Collectors.toList()).get(0);
            // clone hands
            Card newCard = cs.takeCard();
            // clone unknown cards
            unknownCards.remove(newCard);
            if(player == Player.MAX){
                return new BeloteState(Player.MIN, getHand(Player.MAX), getHand(Player.MIN), unknownCards, trick, score);
            } else {
                return new BeloteState(Player.MAX, getHand(Player.MAX), getHand(Player.MIN), unknownCards, trick, score);
            }
        } else {
            //clone trick
            trick.play(player, (Card) action);
            CardStack cs = getHand(player).stream().filter(cardStack -> cardStack.getCard().equals(action)).collect(Collectors.toList()).get(0);
            // clone hands
            Card newCard = cs.takeCard();
            // clone unknown cards
            unknownCards.remove(newCard);

            //clone trick
            trick.play(player, newCard);
            Player lead = trick.getWinner();
            int newScore = lead == Player.MAX ? score + trick.getScore() : score;

            return new BeloteState(lead, getHand(Player.MAX), getHand(Player.MIN), unknownCards, new Trick(lead, false), newScore);
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

    public static void main(String[] args) {
        CardStack cs1 = new CardStack(new Card(Suit.DIAMONDS, CardType.ACE), true);
        CardStack cs2 = new CardStack(new Card(Suit.CLUBS, CardType.KING), false);
        CardStack cs3 = new CardStack(null, true);
        CardStack cs4 = new CardStack(new Card(Suit.CLUBS, CardType.SEVEN), false);



        Set<CardStack> s = new HashSet<>();
        s.add(cs1);
        s.add(cs2);
        s.add(cs3);
        s.add(cs4);

        BeloteState bs = new BeloteState(Player.MAX, s, s, new HashSet<>(), new Trick(Player.MAX, false), 0);
//        bs.getMaxCards().forEach((card -> System.out.println(card)));

        bs.getCardsWithGivenSuit(Player.MAX, Suit.SPADES).forEach((card -> System.out.println(card)));
    }
}
