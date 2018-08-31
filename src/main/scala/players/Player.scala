package players

import actions.Move

trait Player {
  def index: PlayerIndex

  def askForMove: Move
}
