package actions.validators.moves

import actions.Position
import elements.boards.states.BoardState
import elements.pieces.Piece

import scala.annotation.tailrec

object PathConstructor {
  def construct(boardState: BoardState, piece: Piece, finalStep: (Int, Int), f: (Int, Int) => (Int, Int)): List[(Int, Int)] = {
    @tailrec
    def go(current: (Int, Int), result: List[(Int, Int)]): List[(Int, Int)] = {
      if(boardState.isPositionFree(Position(current)).toBoolean && finalStep != current)
        go(f(current._1, current._2), result :+ current)
      else result
    }

    implicit def positionToTuple(p: Position): (Int, Int) = (p.X, p.Y)
    go(piece.position, List.empty)
  }
}
