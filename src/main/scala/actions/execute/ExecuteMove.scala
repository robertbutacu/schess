package actions.execute

import actions.Position
import elements.boards.BoardState
import elements.pieces.Piece

sealed trait ExecuteMove {
  def boardState: BoardState

  def from: Position

  def to: Position

  def go(): BoardState

  protected def updatePiece(board: List[List[Piece]], from: Position, to: Position): List[List[Piece]] = {
    def addPiece(p: Piece, row: List[Piece]): List[Piece] = {
      row.patch(p.position.X, Seq(p), 1)
    }

    def patchBoard(board: List[List[Piece]], piece: Piece) =
      board.patch(piece.position.Y, Seq(addPiece(piece, board(piece.position.Y))), 1)

    val removedInitial = patchBoard(board, board(from.Y)(from.X))
    val updatedMove = patchBoard(removedInitial, board(from.Y)(from.X)(to))

    updatedMove
  }
}

case class NormalMove(boardState: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = {
    ???
  }
}

case class EnPassantMove(boardState: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = ???
}

case class KingCastleMove(boardState: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = ???
}