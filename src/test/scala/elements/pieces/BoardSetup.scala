package elements.pieces

import actions.Position
import elements.boards.BoardState
import elements.boards.information.Players
import elements.boards.states.NormalState
import players._

import scala.language.postfixOps

trait BoardSetup {
  val genericPlayer = AIPlayer("XXX", PlayerOne)
  val genericEnemyPlayer = AIPlayer("YYY", PlayerTwo)
  val genericPosition = Position(4, 4)

  lazy val emptyRow: List[Piece] = (0 to 7) map (i => EmptyPosition(Position(i, 0))) toList
  lazy val pawnRow: List[Piece] = (0 to 7) map { i => Pawn(genericPlayer, Position(i, 0)) } toList

  lazy val rook = Rook(genericPlayer, genericPosition)
  lazy val queen = Queen(genericPlayer, genericPosition)
  lazy val king = King(genericPlayer, genericPosition)
  lazy val bishop = Bishop(genericPlayer, genericPosition)
  lazy val pawn = Pawn(genericPlayer, genericPosition)
  lazy val knight = Knight(genericPlayer, genericPosition)

  lazy val rookDefaultPositionLeft = Rook(genericPlayer, Position(0, 0))
  lazy val rookDefaultPositionLeftEnemy = Rook(genericEnemyPlayer, Position(0, 0))
  lazy val rookDefaultPositionRight = Rook(genericPlayer, Position(7, 0))
  lazy val rookDefaultPositionRightEnemy = Rook(genericEnemyPlayer, Position(7, 0))
  lazy val kingDefaultPosition = King(genericPlayer, Position(4, 0))

  def toEmptyRow(rowIndex: Int): List[Piece] = emptyRow.map(p => EmptyPosition(Position(p.position.X, rowIndex)))

  def mapToPawnRow(rowIndex: Int): List[Piece] = emptyRow.map(p => Pawn(genericPlayer, Position(p.position.X, rowIndex)))

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

    NormalState(finalBoard, Players(genericPlayer, genericEnemyPlayer, 1))
  }
}
