package elements.pieces.moves

import actions.PiecePosition
import elements.boards.BoardState
import elements.boards.states.NormalState
import elements.pieces._
import players._

import scala.language.postfixOps

trait Board {
  val genericPlayer = AIPlayer("XXX", PlayerOne)
  val genericEnemyPlayer = AIPlayer("YYY", PlayerTwo)
  val genericPosition = PiecePosition(4, 4)

  lazy val emptyRow: List[Piece] = (0 to 7) map (i => Empty(PiecePosition(i, 0))) toList
  lazy val pawnRow: List[Piece] = (0 to 7) map { i => Pawn(genericPlayer, PiecePosition(i, 0)) } toList

  lazy val rook = Rook(genericPlayer, genericPosition)
  lazy val queen = Queen(genericPlayer, genericPosition)
  lazy val king = King(genericPlayer, genericPosition)
  lazy val bishop = Bishop(genericPlayer, genericPosition)
  lazy val pawn = Pawn(genericPlayer, genericPosition)
  lazy val knight = Knight(genericPlayer, genericPosition)

  lazy val rookDefaultPositionLeft = Rook(genericPlayer, PiecePosition(0, 0))
  lazy val rookDefaultPositionLeftEnemy = Rook(genericEnemyPlayer, PiecePosition(0, 0))
  lazy val rookDefaultPositionRight = Rook(genericPlayer, PiecePosition(7, 0))
  lazy val rookDefaultPositionRightEnemy = Rook(genericEnemyPlayer, PiecePosition(7, 0))
  lazy val kingDefaultPosition = King(genericPlayer, PiecePosition(4, 0))

  def toEmptyRow(rowIndex: Int): List[Piece] = emptyRow.map(p => Empty(PiecePosition(p.position.X, rowIndex)))

  def mapToPawnRow(rowIndex: Int): List[Piece] = emptyRow.map(p => Pawn(genericPlayer, PiecePosition(p.position.X, rowIndex)))

  def addPiece(p: Piece, row: List[Piece]): List[Piece] = {
    row.patch(p.position.X, Seq(p), 1)
  }

  def getBoard(piece: Piece,
               otherPieces: List[Piece] = List.empty,
               fillFunction: Int => List[Piece] = toEmptyRow): BoardState = {

    def patchBoard(board: List[List[Piece]], piece: Piece) =
      board.patch(piece.position.Y, Seq(addPiece(piece, board(piece.position.Y))), 1)

    val board = 0 to 7 map fillFunction toList

    val withPiece = patchBoard(board, piece)

    val finalBoard = otherPieces.foldRight(withPiece) { (piece, board) =>
      patchBoard(board, piece)
    }

    NormalState(finalBoard)
  }
}
