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

  lazy val board = NoSpecialState(List.empty)

  def toEmptyRow(rowIndex: Int): List[Piece] = emptyRow.map(p => Empty(PiecePosition(p.position.X, rowIndex)))
  def mapToPawnRow(rowIndex: Int): List[Piece] = emptyRow.map(p => Pawn(genericPlayer, PiecePosition(p.position.X, rowIndex)))

  def addPieceToMiddleRow(p: Piece, row: List[Piece]): List[Piece] = {
    row.patch(4, Seq(p), 1)
  }

  def getBoardWithPiece(piece: Piece): BoardState = {
    val emptyBoard = 0 to 7 map toEmptyRow toList

    val withPiece = emptyBoard.slice(0, 4) ::: List(addPieceToMiddleRow(piece, emptyBoard(4)))::: emptyBoard.slice(5, 8)

    NoSpecialState(withPiece)
  }
  def getBoardWithTrappedPiece(piece: Piece): BoardState = ???

//  lazy val rookBoard = List(0 to 7 map mapToEmptyRow)
//  lazy val queenBoard = List(0 to 7 map mapToEmptyRow)
//  lazy val kingBoard = List(0 to 7 map mapToEmptyRow)
//  lazy val bishopBoard = List(0 to 7 map mapToEmptyRow)
//  lazy val pawnBoard = List(0 to 7 map mapToEmptyRow)
//  lazy val knightBoard = List(0 to 7 map mapToEmptyRow)
//
//  lazy val trappedRookBoard = List(0 to 7 map mapToPawnRow)
//  lazy val trappedQueenBoard = List(0 to 7 map mapToPawnRow)
//  lazy val trappedKingBoard = List(0 to 7 map mapToPawnRow)
//  lazy val trappedBishopBoard = List(0 to 7 map mapToPawnRow)
//  lazy val trappedPawnBoard = List(0 to 7 map mapToPawnRow)
//  lazy val trappedKnightBoard = List(0 to 7 map mapToPawnRow)
}
