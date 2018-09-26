package actions.execute.executers

import actions.Position
import actions.execute.BoardCategorisation
import elements.boards.states.{BoardState, NormalState}


case class NormalMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = {
    NormalState(BoardCategorisation.invertBoard(updatePiece(board.pieces, from, to)), board.players.switchTurns)
  }
}