package actions.execute

import actions.Position
import elements.boards.states.{BoardState, NormalState}
import elements.pieces.{EmptyPosition, Piece}
import actions.validators.board.BoardCheckers.Board

sealed trait ExecuteMove {
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

    invertBoard(updatedMove)
  }

  protected def invertBoard(pieces: List[List[Piece]]): List[List[Piece]] = {
    pieces.reverse.map{row =>
      row.map{piece => piece(piece.reverse)}
    }
  }
}

case class NormalMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = {
    NormalState(updatePiece(board.pieces, from, to), board.players.switchTurns)
  }
}

case class EnPassantMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = ???
}

case class KingCastleMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = {
    def adjustCastlingMoves: (Position, Position, Position) =
      if (to.X == 0) // left castling
        (Position(2, 0), Position(0, 0), Position(3, 0))
      else //right castling
        (Position(6, 0), Position(7, 0), Position(5, 0))

    val (kingMove, rookFrom, rookTo) = adjustCastlingMoves

    val kingCastledPieces = updatePiece(board.pieces, from, kingMove)
    val rookAdjustedPieces = updatePiece(kingCastledPieces, rookFrom, rookTo)

    NormalState(rookAdjustedPieces, board.players.switchTurns)
  }
}