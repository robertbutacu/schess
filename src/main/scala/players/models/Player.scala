package players.models

import actions.Move
import players.PlayerIndex

trait Player {
  def index: PlayerIndex

  def askForMove: Move
}
