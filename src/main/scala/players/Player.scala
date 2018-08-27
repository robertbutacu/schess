package players

import actions.Move
import pieces.Board

trait Player {
  def askForMove(board: Board): Move
}
