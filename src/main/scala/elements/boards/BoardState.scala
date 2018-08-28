package elements.boards

import actions.PiecePosition
import players.Player

trait BoardState {
  def player1: Player
  def player2: Player
  def playerTurn: Int
  def player1KingPosition: PiecePosition
  def player2KingPosition: PiecePosition

  def next: BoardState
}
