package belote;

import game.Action;
import game.Player;
import game.State;

import java.util.*;
import java.util.stream.Collectors;

import static belote.Constants.TRUMP;

public class BeloteState implements State {
    public static final CardComparator COMPARATOR = new CardComparator();

    private final Player player;

    private final List<CardStack> maxHand;
    private final List<CardStack> minHand;
    private final Trick trick;
    private final int score;

    private final int minPoints;

    public BeloteState(Player player, List<CardStack> maxHand, List<CardStack> minHand, Trick trick, int score, int minPoints) {
        this.player = player;
        this.maxHand = maxHand;
        this.minHand = minHand;
        this.trick = trick;
        this.score = score;
        this.minPoints = minPoints;
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
        List<CardStack> newMaxHand = maxHand.stream().map(cardStack -> new CardStack(cardStack)).collect(Collectors.toList());
        List<CardStack> newMinHand = minHand.stream().map(cardStack -> new CardStack(cardStack)).collect(Collectors.toList());

        List<CardStack> currentPlayerHand = player == Player.MAX ? newMaxHand : newMinHand;

        // Find from which CardStack is the given Card
        CardStack cs = currentPlayerHand.stream().filter(cardStack -> {
            Card card = cardStack.getCard();
            boolean bool = Objects.equals(card, action);
            return bool;
        }).collect(Collectors.toList()).get(0);

        cs.takeCard();

        BeloteState beloteState;

        if(newTrick.isEmpty()){
            newTrick.play(player, (Card) action);
            if(player == Player.MAX){
                beloteState = new BeloteState(Player.MIN, newMaxHand, newMinHand, newTrick, score, minPoints);
                return beloteState;
            } else {
                beloteState = new BeloteState(Player.MAX, newMaxHand, newMinHand, newTrick, score, minPoints);
                return beloteState;
            }
        } else {
            newTrick.play(player, (Card) action);
            Player lead = newTrick.getWinner();
            int newScore = lead == Player.MAX ? score + newTrick.getScore() : score;
            int newMinScore = lead == Player.MIN ? minPoints + newTrick.getScore() : minPoints;

            beloteState = new BeloteState(lead, newMaxHand, newMinHand, new Trick(lead, newTrick.getTrickNumber() + 1), newScore, newMinScore);
            return beloteState;
        }
    }

    public List<CardStack> getHand(Player player){
        List<CardStack> hand = player == Player.MAX ? maxHand : minHand;
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

    public Trick getTrick(){
        return trick;
    }

    public int getScore(){
        return score;
    }

    public int getMinPoints(){
        return minPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeloteState)) return false;
        BeloteState that = (BeloteState) o;

        return score == that.score && getPlayer() == that.getPlayer() && areTheSame(maxHand, that.maxHand) && areTheSame(minHand, that.minHand) && trick.equals(that.trick);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayer(), maxHand, minHand, trick, score);
    }

    @Override
    public String toString() {
        return "BeloteState{" +
                "player=" + player +
                ", maxHand=" + maxHand +
                ", minHand=" + minHand +
                ", trick=" + trick +
                ", MAX Points=" + score +
                ", MIN Points=" + minPoints +
                '}';
    }

    private static boolean areTheSame(List<CardStack> l1, List<CardStack> l2){
        Set<CardStack> s1 = l1.stream().filter(cardStack -> !cardStack.isEmpty()).collect(Collectors.toSet());
        Set<CardStack> s2 = l2.stream().filter(cardStack -> !cardStack.isEmpty()).collect(Collectors.toSet());

        Iterator<CardStack> iterator = s1.iterator();

        while(iterator.hasNext()){
            CardStack current = iterator.next();
            if(!s2.contains(current)){
                return false;
            }else {
                s2.remove(current);
            }
        }

        return s2.isEmpty();
    }

}
