package players

import actions.Move

trait Player {
  def index: Int

  def askForMove: Move
}
