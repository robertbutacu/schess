package elements.pieces.moves

import actions.PiecePosition
import elements.boards.BoardState
import elements.boards.states.NoSpecialState
import elements.pieces._
import players.AIPlayer

import scala.language.postfixOps

trait Board {
  val genericPlayer = AIPlayer("XXX", 1)
  val genericPosition = PiecePosition(4, 4)

  lazy val emptyRow: List[Piece] = (0 to 7) map (i => Empty(PiecePosition(i, 0))) toList
  lazy val pawnRow: List[Piece] = (0 to 7) map { i => Pawn(genericPlayer, PiecePosition(i, 0)) } toList

  lazy val rook = Rook(genericPlayer, genericPosition)
  lazy val queen = Queen(genericPlayer, genericPosition)
  lazy val king = King(genericPlayer, genericPosition)
  lazy val bishop = Bishop(genericPlayer, genericPosition)
  lazy val pawn = Pawn(genericPlayer, genericPosition)
  lazy val knight = Knight(genericPlayer, genericPosition)

  def toEmptyRow(rowIndex: Int): List[Piece] = emptyRow.map(p => Empty(PiecePosition(p.position.X, rowIndex)))

  def mapToPawnRow(rowIndex: Int): List[Piece] = emptyRow.map(p => Pawn(genericPlayer, PiecePosition(p.position.X, rowIndex)))

  def addPieceToMiddleRow(p: Piece, row: List[Piece]): List[Piece] = {
    row.patch(4, Seq(p), 1)
  }

  def getBoard(piece: Piece, fillFunction: Int => List[Piece] = toEmptyRow): BoardState = {
    val board = 0 to 7 map fillFunction toList


    val withPiece = board.patch(4, Seq(addPieceToMiddleRow(piece, board(4))), 1)

    NoSpecialState(withPiece)
  }
}
