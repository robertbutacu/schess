package elements.boards

import actions.PiecePosition
import elements.boards.Board.{isDiagonalMove, isStraightMove}
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.{Empty, Piece}
import errors.{ChessError, IllegalMove}

object BoardUtils {
  def isKingInCheckState(pieces: List[List[Piece]], playerTurn: Int, kingsPositions: KingsPositions): Boolean = {
    require(playerTurn == 1 || playerTurn == 2)

    def filterOppositePlayerPieces(): List[Piece] =
      for {
        row <- pieces
        piece <- row
        owner <- piece.owner
        if owner.index == nextPlayerIndex(playerTurn)
      } yield piece

    val playerKing = kingsPositions.getKingPosition(playerTurn)

    val possibleDangers = filterOppositePlayerPieces()

    possibleDangers.exists(p => Board.isValidMove(p, playerKing))
  }

  def nextPlayerIndex(playerTurn: Int): Int = 3 - playerTurn

  def isClearPath(board: Board, from: PiecePosition, to: PiecePosition): Either[Boolean, ChessError] = {
    require(movedPiece)

    def movedPiece: Boolean = from.X != to.X || from.Y != to.Y

    (isDiagonalMove(from, to), isStraightMove(from, to)) match {
      case (true, _) =>
        def getDirection(from: Int, to: Int): Int = {
          from - to match {
            case 0 => 0
            case negative if negative < 0 => -1
            case positive if positive > 0 => +1
          }
        }

        val xDirection = getDirection(from.X, to.X)
        val yDirection = getDirection(from.Y, to.Y)

        def checkDiagonal(currX: Int, currY: Int): Boolean = {
          if (currX == to.X && currY == to.Y) true
          else isEmptyPosition(board, currX, currY) && checkDiagonal(currX + xDirection, currY + yDirection)
        }

        Left(checkDiagonal(from.X + xDirection, to.Y + yDirection))
      case (_, true) =>
        if (from.X != to.X) Left((from.X + 1 to to.X).forall { x => isEmptyPosition(board, x, to.Y) })
        else Left((from.Y + 1 to to.X).forall { y => isEmptyPosition(board, to.X, y) })
      case (_, _) => Right(IllegalMove)
    }
  }

  private def isEmptyPosition(board: Board, X: Int, Y: Int) = board.table(X)(Y) match {
    case Empty(_) => true
    case _ => false
  }

}
