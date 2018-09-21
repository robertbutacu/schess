package elements.boards

import actions.Position
import elements.boards.states.BoardState
import elements.pieces.Piece

case class PreviousMove(board: BoardState, piece: Piece, to: Position)
