package elements.boards

import actions.Position
import elements.pieces.Piece
import players.Player

object Board {

  def isStraightMove(from: Position, to: Position): Boolean = from.X == to.X || from.Y == to.Y

  def isDiagonalMove(from: Position, to: Position): Boolean =
    Math.abs(from.X - to.X) == Math.abs(from.Y - to.Y)

  def isValidMove(piece: Piece, to: Position): Boolean = ???

  def isStall(board: BoardState, playerToPlay: Player): Boolean = ???

  def isEndGame(board: BoardState, playerToPlay: Player): Boolean = ???

  def isCheckState(board: BoardState, playerToPlay: Player): Boolean = ???

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
