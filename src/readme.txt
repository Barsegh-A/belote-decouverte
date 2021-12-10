The code consist of two packages.
Game package contains the same components as used in Homework 6.
A method is added to the abstract class Printing that prints the course of the game given the initial state, a terminal
test and the strategy (assuming both players play optimally).
A method called getAverageBranchingFactor is added to the Search interface and it is implemented in both MinimaxSearch
and AlphaBetaSearch.
MinimaxSearch and AlphaBetaSearch classes implement corresponding tree and graph search algorithms. In demos, only
the graph search variant is used.

Belote package contains the key components for Belote.
- Suit enum names all possible suits - Diamond, Clubs, Hearts, Spades
- CardType enum names all card types - 7, 8, 9, 10, J, Q, K, A with their respective ordering and values for both trump
and no-trump cases.
- Card class represents a card that has its suit and type. It implements the Action interface. Also, it stores its value
based on the fact whether it is a trump or not.
- CardStack represents the notion of a card stack described in the paper, it has top and bottom cards.
- Trick represents the notion of the trick described in the paper, it stores the cards played by Max and Min players,
the lead. Additionally, a score for trick is calculated as the sum of the values of the card in it. Trick number is used
to identify the last trick in order to give its winner additional 10 points. Note that, with n suits in the deck,
there will be 4*n tricks. To find out who is the winner of the trick, CardComparator is implemented.
- CardComparator is a comparator that given two cards identifies which will win the trick assuming the the first given
card is played by the lead of the trick. Compare method is implemented following the rules of the game. More details can
be found in the comments for each if statement.
- BeloteState implements the State interface and represents the notion of the state described in the paper. It includes
the hands of Max and Min players, the trick, the player to play and the current score(points won by Max). Additionally,
we store the points won by Min player as well to easily implement Forward Pruning. getApplicableActions is implemented
following the game rules. More details can be found in the comments. In getActionResult, first the card stack is found
from which the selected card should be played. Then if it is the action of the lead player, a new state is created with
corresponding changes of lead's hand and the trick. If after an action trick becomes full, the winner of the trick is
determined, a new state is created with corresponding changes of players' hands, score. New trick is created for the
resulting state.
- BeloteTerminalTest extends TerminalTest and implements the notion of a terminal state in Belote. A BeloteState is
terminal, if both players have played all their cards. The utility of a terminal state is implemented as described in
paper with two versions - the score and win/lose. Furthermore, the logic of forward pruning is implemented as well.
Details can be found in comments.
- Deck class is used to generate cards given the number of suits(1-4). The shuffle method is used to shuffle the deck.
Note that we set trumps cards at the moment they are created based on the fixed trump.
- BelotePrinting extends the abstract Printing class.
- Constants class stores several aspects such as the number of suits, boolean flags for the utility function and forward
pruning. It is used to control the demo.
- BeloteDemo is used for demonstrations of the algorithm. Deal method is used to mimic the dealing phase. As a result,
initial belote state is returned. In main method, instances of BeloteState, BeloteTerminalState, BelotePrinting,
MinimaxSearch and AlphaBetaSeacrh are created based on the specified constants. Algorithms are run to solve the game.
Initial state is printed as well as some statistics for each algorithm and finally the optimal play determined by the
strategy returned by AlphaBetaSearch.

Also, all classes that are used in BeloteState and BeloteState itself hashCode and equals methods are implemented
correspondingly.

Finally, to run the demo, corresponding values should be given to the number of suits, win/lose, forward pruning in Constants.

