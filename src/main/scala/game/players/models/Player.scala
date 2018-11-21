package game.players.models

import actions.Move
import game.players.PlayerIndex

trait Player {
  def index: PlayerIndex

  def askForMove: Move
}
