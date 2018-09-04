package elements.boards.validators

import actions.Position
import elements.boards.BoardState
import elements.pieces.Piece
import players.Player

object BoardValidators {

  implicit class BoardValidator(board: BoardState) {

    def isStraightMove(from: Position, to: Position): Boolean = from.X == to.X || from.Y == to.Y

    def isDiagonalMove(from: Position, to: Position): Boolean =
      Math.abs(from.X - to.X) == Math.abs(from.Y - to.Y)

    def isValidMove(piece: Piece, to: Position): Boolean = ???

    def isStall(board: BoardState, playerToPlay: Player): Boolean = ???

    def isEndGame(board: BoardState, playerToPlay: Player): Boolean = ???

    def isCheckState(board: BoardState, playerToPlay: Player): Boolean = ???
  }
}
