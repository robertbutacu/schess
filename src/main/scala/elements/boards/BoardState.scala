package elements.boards

import actions.PiecePosition
import elements.pieces.Piece
import players.Player

trait BoardState {
  def player1: Player
  def player2: Player
  def playerTurn: Int
  def player1KingPosition: PiecePosition
  def player2KingPosition: PiecePosition

  def pieces: List[List[Piece]]

  def isPlayerKingUncovered: Boolean

  def next: BoardState
}
