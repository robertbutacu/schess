package elements.pieces.moves

import actions.PiecePosition
import elements.boards.states.NoSpecialState
import elements.pieces._
import players.AIPlayer

trait Board {
  val genericPlayer = AIPlayer("XXX", 1)
  val genericPosition = PiecePosition(4, 4)

  lazy val rook = Rook(genericPlayer, genericPosition)
  lazy val queen = Queen(genericPlayer, genericPosition)
  lazy val king = King(genericPlayer, genericPosition)
  lazy val bishop = Bishop(genericPlayer, genericPosition)
  lazy val pawn = Pawn(genericPlayer, genericPosition)
  lazy val knight = Knight(genericPlayer, genericPosition)

  lazy val board = NoSpecialState(List.empty)

}
