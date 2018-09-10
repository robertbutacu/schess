package actions.execute

import elements.boards.BoardState
import elements.boards.information.Players
import elements.pieces.Piece
import elements.pieces.moves.MoveValidator.ops._
import elements.pieces.moves.MoveValidator._
import elements.pieces.moves.BoardCheckers._


object BoardCategorisation {
  def isCheckmate(board: BoardState, players: Players): Boolean = {
    def isEnemyPlayer(p: Piece) = p.owner.forall(player => player.index != players.getPlayerTurn.index)

    val playerKing = board.getKingForCurrentPlayer

    val possibleMoves = for {
      row <- board.pieces
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
}
