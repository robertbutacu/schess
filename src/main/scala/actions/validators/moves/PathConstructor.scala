package actions.validators.moves

import game.elements.boards.Position
import game.elements.boards.states.BoardState
import game.elements.pieces.Piece

import scala.annotation.tailrec

object PathConstructor {
  def construct(boardState: BoardState, piece: Piece, finalStep: Position, f: (Int, Int) => (Int, Int)): List[(Int, Int)] = {
    val finalPosition = (finalStep.X, finalStep.Y)

    @tailrec
    def go(current: (Int, Int), result: List[(Int, Int)]): List[(Int, Int)] = {
      def canStillAdvance: Boolean = boardState.isPositionFree(Position(current)).toBoolean && finalPosition != current

      if(canStillAdvance) go(f(current._1, current._2), result :+ current)
      else                result
    }

    go(f(piece.position.X, piece.position.Y), List.empty)
  }

  private implicit def positionToTuple(p: Position): (Int, Int) = (p.X, p.Y)
}
