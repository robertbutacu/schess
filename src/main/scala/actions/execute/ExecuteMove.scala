package actions.execute

import actions.Position
import elements.boards.BoardState
import elements.boards.states.NormalState
import elements.pieces.{EmptyPosition, Piece}

sealed trait ExecuteMove {
  def board: BoardState

  def from: Position

  def to: Position

  def go(): BoardState

  protected def updatePiece(board: List[List[Piece]], from: Position, to: Position): List[List[Piece]] = {
    def addPiece(p: Piece, row: List[Piece]): List[Piece] = {
      row.patch(p.position.X, Seq(p), 1)
    }

    def patchBoard(board: List[List[Piece]], piece: Piece) =
      board.patch(piece.position.Y, Seq(addPiece(piece, board(piece.position.Y))), 1)

    val removedInitial = patchBoard(board, EmptyPosition(from))
    val updatedMove = patchBoard(removedInitial, board(from.Y)(from.X)(to))
    //the coordonates of the pieces are being updated above here directly

    updatedMove
  }
}

case class NormalMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = {
    NormalState(updatePiece(board.pieces, from, to))
  }
}

case class EnPassantMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = ???
}

case class KingCastleMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = {
    val king = board.pieces(from.Y)(from.X)
    val rook = board.pieces(to.Y)(from.X)

    val kingCastled = updatePiece(board.pieces, from, to)

    //TODO change king to coordonates, doesnt work as you thought
    //figure out a formula where the rook goes to its position
    // the king aint no problem, its just patching to the to position
    ???
  }
}