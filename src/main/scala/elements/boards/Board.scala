package elements.boards

import actions.PiecePosition
import elements.pieces.Piece
import players.Player

case class Board(table: List[List[Piece]], player1: Player, player2: Player)

object Board {

  def isStraightMove(from: PiecePosition, to: PiecePosition): Boolean = from.X == to.X || from.Y == to.Y

  def isDiagonalMove(from: PiecePosition, to: PiecePosition): Boolean =
    Math.abs(from.X - to.X) == Math.abs(from.Y - to.Y)

  def isValidMove(piece: Piece, to: PiecePosition): Boolean = ???

  def isStall(board: Board, playerToPlay: Player): Boolean = ???

  def isEndGame(board: Board, playerToPlay: Player): Boolean = ???

  def isCheckState(board: Board, playerToPlay: Player): Boolean = ???

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
