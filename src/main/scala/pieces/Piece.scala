package pieces

trait Piece {
  def isValidMove(board: Board): Boolean
}
