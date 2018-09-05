package actions.execute

import elements.boards.BoardState
import elements.boards.information.Players
import elements.pieces.moves.MoveValidator.ops._
import elements.pieces.moves.MoveValidator._

object BoardCategorisation {
  def getNextState(pieces: BoardState, players: Players): BoardState = ???


  def isCheckmate(board: BoardState, players: Players): Boolean = {
    val playerKing = board.pieces(board.getKingPositionForCurrentPlayer.Y)(board.getKingPositionForCurrentPlayer.X)

    val possibleMoves = for {
      row <- board.pieces
      piece <- row
      if board.isValidMove(playerKing, piece.position)
    } yield piece.position

    board.pieces.exists(row => row.exists(p => possibleMoves.exists(pm => board.isValidMove(p, pm))))
  }
}
