package elements.boards

import players.Player

object PlayChess {
  /**
    * Interaction will be in the following way:
    *1. choose a player to start
    *2. player X starts
    * Recursive:
    * if not end game:
    * ask for move from player
    * validate move
    * if valid move:
    * make move
    * else:
    * ask for a move again
    * recursive call with the other player
    * if end game OR stall:
    * other player wins( meaning current player has no available moves )
    */
  def play: Player = ???
}
