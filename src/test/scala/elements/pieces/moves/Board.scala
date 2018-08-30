package elements.pieces.moves

import actions.PiecePosition
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

  lazy val board = NoSpecialState(List.empty)

  def mapToRow(rowIndex: Int) = emptyRow.map(p => Empty(PiecePosition(p.position.X, rowIndex)))

  def addPieceToMiddleRow(p: Piece, row: List[Piece]): List[Piece] = ???

  lazy val rookBoard = List(0 to 7 map mapToRow)
  lazy val queenBoard = List(0 to 7 map mapToRow)
  lazy val kingBoard = List(0 to 7 map mapToRow)
  lazy val bishopBoard = List(0 to 7 map mapToRow)
  lazy val pawnBoard = List(0 to 7 map mapToRow)
  lazy val knightBoard = List(0 to 7 map mapToRow)


}
