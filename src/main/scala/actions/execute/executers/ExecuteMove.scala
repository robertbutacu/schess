package actions.execute.executers

import actions.Position
import elements.boards.states.BoardState
import elements.pieces.{EmptyPosition, Piece}

trait ExecuteMove {
  def board: BoardState

  def from: Position

  def to: Position

  def go(): BoardState

  protected def updatePiece(board: List[List[Piece]], from: Position, to: Position): List[List[Piece]] = {
    def addPiece(p: Piece, row: List[Piece]): List[Piece] = {
      row.patch(p.position.X, Seq(p), 1)
    }

    def patchBoard(board: List[List[Piece]], piece: Piece): List[List[Piece]] =
      board.patch(piece.position.Y, Seq(addPiece(piece, board(piece.position.Y))), 1)

    val removedInitial = patchBoard(board, EmptyPosition(from))
    val updatedMove = patchBoard(removedInitial, board(from.Y)(from.X)(to))
    //the coordonates of the pieces are being updated above here directly

    updatedMove
  }
}
