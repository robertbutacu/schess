package game.elements.boards

import game.elements.boards.states.BoardState
import game.elements.pieces.Piece

case class PreviousMove(board: BoardState, piece: Piece, to: Position)
