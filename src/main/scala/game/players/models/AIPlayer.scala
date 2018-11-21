package game.players.models

import actions.Move
import game.players.PlayerIndex

case class AIPlayer(name: String, index: PlayerIndex) extends Player {
  override def askForMove: Move = ???
}
