package elements.boards

import actions.Position
import elements.pieces.Piece

case class PreviousMove(board: BoardState, piece: Piece, to: Position)
