package belote;

import game.Player;

public class Trick {
    private Card maxCard;
    private Card minCard;
    private Player lead;
    private int score;

    private boolean isLastTrick;

    public Trick(Player lead){
        this.lead = lead;
    }

    public Trick(Player lead, boolean isLastTrick){
        this(lead);
        this.isLastTrick = isLastTrick;
    }

    public void play(Player player, Card c){
        if(player == Player.MAX) {
            maxCard = c;
        } else {
            minCard = c;
        }
    }

    public Player getWinner(){
        if(maxCard == null || minCard == null){
            throw new RuntimeException("Trick is not full...");
        }

        // TODO make a static comparator for a game and use it for all tricks
        CardComparator comparator = new CardComparator();

        Player winner = comparator.compare(maxCard, minCard) < 0 ? Player.MIN : Player.MAX;

        return winner;
    }

    public Card getFirstCard(){
         if(lead == Player.MAX){
             return maxCard;
         }

         return minCard;
    }

    public int getScore(){
        int score = maxCard.getValue() + minCard.getValue();

        if(isLastTrick){
            score += 10;
        }

        return score;
    }

    public boolean isEmpty(){
        return getFirstCard() == null;
    }
}
