package players
import actions.Move
import pieces.Board

case class AIPlayer(name: String) extends Player {
  override def askForMove(board: Board): Move = ???
}
