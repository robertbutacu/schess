package players

import actions.Move
import elements.boards.Board

trait Player {
  def askForMove(board: Board): Move
}
