package elements.boards

import actions.PiecePosition
import elements.pieces.{Empty, Piece}
import errors.{ChessError, IllegalMove}
import players.Player

case class Board(table: List[List[Piece]], player1: Player, player2: Player)


//TODO maybe make the board to be an ADT for all possible table position? check/piece taken out/stallmate/checkmate
//TODO and make that act independently
object Board {
  //stall is when
  def isStall(board: Board, playerToPlay: Player): Boolean = ???

  def isEndGame(board: Board, playerToPlay: Player): Boolean = ???

  def isCheckState(board: Board, playerToPlay: Player): Boolean = ???

  def isObstructedPath(board: Board, from: PiecePosition, to: PiecePosition): Either[Boolean, ChessError] = {
    require(movedPiece)

    def isStraightMove: Boolean = from.X == to.X || from.Y == to.Y

    def isDiagonalMove: Boolean = Math.abs(from.X - to.X) == Math.abs(from.Y - to.Y)

    def movedPiece: Boolean = from.X != to.X || from.Y != to.Y

    (isDiagonalMove, isStraightMove) match {
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

  /**
    * Interaction will be in the following way:
    *1. choose a player to start
    *2. player X starts
    * Recursive:
    * if not end game:
    * ask for move from player
    * validate move
    * if valid move:
    * make move
    * else:
    * ask for a move again
    * recursive call with the other player
    * if end game OR stall:
    * other player wins( meaning current player has no available moves )
    */
  def play: Player = ???

  private def isEmptyPosition(board: Board, X: Int, Y: Int) = board.table(X)(Y) == Empty
}
