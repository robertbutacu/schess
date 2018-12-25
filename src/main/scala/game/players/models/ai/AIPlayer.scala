package game.players.models.ai

import actions.Move
import game.players.PlayerIndex
import game.players.models.Player
import game.players.models.ai.layers.Difficulty

case class AIPlayer(name: String, index: PlayerIndex, difficulty: Difficulty) extends Player {
  override def askForMove(): Move = ???
}
