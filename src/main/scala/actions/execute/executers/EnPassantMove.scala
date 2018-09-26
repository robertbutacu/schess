package actions.execute.executers

import actions.Position
import elements.boards.states.BoardState


case class EnPassantMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = ???
}