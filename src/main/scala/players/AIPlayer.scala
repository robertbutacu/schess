package players

import actions.Move

case class AIPlayer(name: String, index: PlayerIndex) extends Player {
  override def askForMove: Move = ???
}
