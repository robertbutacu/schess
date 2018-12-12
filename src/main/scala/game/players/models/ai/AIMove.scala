package game.players.models.ai

import actions.Move
import actions.validators.board.BoardCheckers.Board

trait AIMove[D] {
  def executeMove(board: Board): Move
}

object AIMove {
  def apply[D](implicit executioner: AIMove[D]): AIMove[D] = executioner

  implicit def easyBotMove: AIMove[Easy] = new AIMove[Easy] {
    override def executeMove(board: Board): Move = ???
  }

  implicit def mediumBotMove: AIMove[Medium] = new AIMove[Medium] {
    override def executeMove(board: Board): Move = ???
  }

  implicit def hardBotMove: AIMove[Hard] = new AIMove[Hard] {
    override def executeMove(board: Board): Move = ???
  }
}
