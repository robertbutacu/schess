package elements.boards

import players.Player

trait BoardState {
  def player1: Player
  def player2: Player
  def playerTurn: Int

  def next: BoardState
}
