package players.models

import actions.Move
import players.PlayerIndex

case class AIPlayer(name: String, index: PlayerIndex) extends Player {
  override def askForMove: Move = ???
}
