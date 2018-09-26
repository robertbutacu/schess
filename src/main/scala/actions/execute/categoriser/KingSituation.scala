package actions.execute.categoriser

import elements.boards.states.{BoardState, CheckState}

sealed trait KingSituation {
  def board: BoardState
  def next: BoardState
}

case class CheckSituation(board: BoardState) extends KingSituation {
  override def next: BoardState = CheckState(board.pieces, board.swapPlayerTurn)
}
