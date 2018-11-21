package actions.execute.executers

import game.elements.boards.Position
import game.elements.boards.states.{BoardState, NormalState}


case class NormalMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = {
    BoardState.nextPhase(updatePiece(board.pieces, from, to), board.players.switchTurns)
  }
}