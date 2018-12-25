package game.players.models.ai.layers

import actions.Move
import actions.validators.board.BoardCheckers.Board

sealed trait Difficulty

object Difficulty {
  def executeMove(difficulty: Difficulty, board: Board): Move = {
    difficulty match {
      case _: Easy   => ??? //AIMove[Easy].executeMove(board)
      case _: Medium => ??? //AIMove[Medium].executeMove(board)
      case _: Hard   => ??? //AIMove[Hard].executeMove(board)
    }
  }
}

case class Easy()   extends Difficulty
case class Medium() extends Difficulty
case class Hard()   extends Difficulty
