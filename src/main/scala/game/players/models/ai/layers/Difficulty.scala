package game.players.models.ai.layers

import actions.Move
import actions.validators.board.BoardCheckers.Board

sealed trait Difficulty {
  def maxDepth: Int
}

object Difficulty {
  def executeMove(difficulty: Difficulty, board: Board): Move = {
    difficulty match {
      case _: Easy   => ??? //AIMove[Easy].executeMove(board)
      case _: Medium => ??? //AIMove[Medium].executeMove(board)
      case _: Hard   => ??? //AIMove[Hard].executeMove(board)
    }
  }
}

case class Easy()   extends Difficulty {
  override def maxDepth: Int = 3
}

case class Medium() extends Difficulty {
  override def maxDepth: Int = 4
}

case class Hard()   extends Difficulty {
  override def maxDepth: Int = 5
}
