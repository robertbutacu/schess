import actions.PiecePosition
import elements.pieces.Rook
import players.HumanPlayer
import elements.pieces.moves.MoveValidator._

object Main extends App {
  val rook = Rook(HumanPlayer("rob", 1), PiecePosition(1,2))

  println(rook.isValidMove(PiecePosition(3, 2)))

}
