package pieces

import actions.PiecePosition

case class Board(table: List[List[Piece]])

object Board {
  def isEndGame: Boolean = ???

  // somehow, figure out whether its a straight move, or a diagonal one
  // also, figure out when it's not a legal move
  def isObstructedPath(board: Board, from: PiecePosition, to: PiecePosition): Boolean = ???
}
