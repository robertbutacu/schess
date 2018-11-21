package actions.execute.executers

import game.elements.boards.Position
import game.elements.boards.states.BoardState


case class EnPassantMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = {
    BoardState.nextPhase(updatePiece(board.pieces, from, to), board.players.switchTurns)
  }
}