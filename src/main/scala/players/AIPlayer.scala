package players
import actions.Move
import elements.boards.Board

case class AIPlayer(name: String) extends Player {
  override def askForMove(board: Board): Move = ???
}
