package players

import actions.Move

case class AIPlayer(name: String, index: Int) extends Player {
  override def askForMove: Move = ???
}
