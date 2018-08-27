package pieces

import actions.PiecePosition
import players.Player

case class Board(table: List[List[Piece]], player1: Player, player2: Player)

object Board {
  //stall is when
  def isStall(board: Board, playerToPlay: Player): Boolean = ???


  def isEndGame(board: Board, playerToPlay: Player): Boolean = ???

  // somehow, figure out whether its a straight move, or a diagonal one
  // also, figure out when it's not a legal move
  def isObstructedPath(board: Board, from: PiecePosition, to: PiecePosition): Boolean = ???

  /**
    Interaction will be in the following way:
    1. choose a player to start
    2. player X starts
    Recursive:
      if not end game:
          ask for move from player
          validate move
          if valid move:
            make move
          else:
            ask for a move again
          recursive call with the other player
      if end game OR stall:
          other player wins( meaning current player has no available moves )


   */
  def play: Player = ???
}
