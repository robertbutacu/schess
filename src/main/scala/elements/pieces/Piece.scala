package elements.pieces

import actions.PiecePosition
import players.Player

trait Piece {
  def owner: Option[Player]
  def piecePosition: PiecePosition
}