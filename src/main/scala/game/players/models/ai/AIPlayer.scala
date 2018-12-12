package game.players.models.ai

import actions.Move
import game.players.PlayerIndex
import game.players.models.Player

case class AIPlayer(name: String, index: PlayerIndex, difficulty: Difficulty) extends Player {
  override def askForMove(): Move = ???
}
