package belote;

import game.Player;

import java.util.Objects;

import static belote.BeloteState.COMPARATOR;

public class Trick{

    private Card maxCard;
    private Card minCard;
    private Player lead;
    private int score;
    private int trickNumber;

    public Trick(Player lead, int trickNumber){
        this.lead = lead;
        this.trickNumber = trickNumber;
    }

    public Trick(Card maxCard, Card minCard, Player lead, int score, int trickNumber){
        this.maxCard = maxCard;
        this.minCard = minCard;
        this.lead = lead;
        this.score = score;
        this.trickNumber = trickNumber;
    }

    public Trick(Trick trick1){
//        this(null, null, trick1.lead, trick1.score, trick1.isLastTrick);
        Card maxCard = trick1.maxCard == null ? null : new Card(trick1.maxCard);
        Card minCard = trick1.minCard == null ? null : new Card(trick1.minCard);
        this.maxCard = maxCard;
        this.minCard = minCard;
        this.lead = trick1.lead;
        this.score = trick1.score;
        this.trickNumber = trick1.trickNumber;
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

        Player winner;

        if(lead == Player.MAX){
            winner = COMPARATOR.compare(maxCard, minCard) < 0 ? Player.MIN : Player.MAX;
        } else {
            winner = COMPARATOR.compare(minCard, maxCard) < 0 ? Player.MAX : Player.MIN;
        }


        return winner;
    }


    public Card getFirstCard(){
         if(lead == Player.MAX){
             return maxCard;
         }

         return minCard;
    }

    public int getScore(){
        if(maxCard == null || minCard == null){
            return 0;
        }

        int score = maxCard.getValue() + minCard.getValue();

        if(isLastTrick()){
            score += 10;
        }

        return score;
    }

    public boolean isEmpty(){
        return getFirstCard() == null;
    }

    public boolean isLastTrick(){
        return trickNumber == 16;
    }

    public int getTrickNumber(){
        return trickNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trick)) return false;
        Trick trick = (Trick) o;
        return trickNumber == trick.trickNumber && Objects.equals(maxCard, trick.maxCard) && Objects.equals(minCard, trick.minCard) && lead == trick.lead;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxCard, minCard, lead, trickNumber);
    }

    @Override
    public String toString() {
        return "Trick{" +
                "maxCard=" + maxCard +
                ", minCard=" + minCard +
                ", lead=" + lead +
                ", score=" + score +
                ", trick number=" + trickNumber +
                '}';
    }

    public static void main(String[] args) {
        Card c1 = new Card(Suit.CLUBS, CardType.QUEEN);
//        c1.setTrump();
        Card c2 = new Card(Suit.SPADES, CardType.JACK);
//        c2.setTrump();

        Player lead = Player.MAX;

        Trick trick = new Trick(lead, 1);

        trick.play(lead, c1);
        trick.play(Player.MIN, c2);

        System.out.println(trick.getWinner());;


    }
}
