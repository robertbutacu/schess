package actions.execute

import elements.boards.states.BoardState
import elements.pieces.Piece
import actions.validators.moves.MoveValidator.ops._
import actions.validators.moves.MoveValidator._
import actions.validators.board.BoardCheckers._
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import players.Players

object BoardCategorisation {
  def isCheckmate(board: BoardState, players: Players): Boolean = {
    def isEnemyPlayer(p: Piece) = p.owner.forall(player => player.index != players.getPlayerTurn.index)

    val playerKing = board.getKingForCurrentPlayer

    val possibleMoves = for {
      row   <- board.pieces
      piece <- row
      if board.isValidMove(playerKing, piece.position)
    } yield board.getPossiblePositionForKing(playerKing, piece.position)

    // there exists a move on the board which would threaten the king in his possible new position
    board.pieces
      .exists { row =>
        row.exists { p =>
          possibleMoves
            .exists {
              pm => isEnemyPlayer(p) && board.isValidMove(p, pm)
            }
        }
      }
  }

  // player is not in check, but for all moves, it would result a check
  def isStalemate(board: BoardState, players: Players): Boolean = {
    val possibleMoves = for {
      row                 <- board.pieces
      piece               <- row
      if piece.owner.forall(p => p.index.toInt == players.playerTurn)
      possibleMoveRow     <- board.pieces
      possibleEndPosition <- possibleMoveRow
      if board.isValidMove(piece, possibleEndPosition.position)
      updatedBoard = MoveCategorisation.categorise(board, piece.position, possibleEndPosition.position)
      if updatedBoard.isKingNotInCheck
    } yield piece

    possibleMoves.isEmpty
  }
}
