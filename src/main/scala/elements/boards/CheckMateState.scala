package elements.boards
import actions.PiecePosition
import elements.pieces.Piece
import players.Player

case class CheckMateState() extends BoardState {
  override def player1: Player = ???

  override def player2: Player = ???

  override def playerTurn: Int = ???

  override def player1KingPosition: PiecePosition = ???

  override def player2KingPosition: PiecePosition = ???

  override def next: BoardState = ???

  override def isPlayerKingUncovered: Boolean = ???

  override def pieces: List[List[Piece]] = ???
}
