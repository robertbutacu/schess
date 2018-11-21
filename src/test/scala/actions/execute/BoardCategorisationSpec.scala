package actions.execute

import game.elements.boards.Position
import game.elements.pieces.{Bishop, BoardSetup, King, Rook}
import org.scalatest.{FlatSpec, Matchers}
import game.players.Players

class BoardCategorisationSpec extends FlatSpec with Matchers with BoardSetup {
  //"When the king is in checkmate, the function"
  ignore should "respond positive" in {
    val king = King(genericPlayer, Position(0, 0))
    val enemyBishop = Bishop(genericEnemyPlayer, Position(0, 2))//threatening 11
    val enemyBishop2 = Bishop(genericEnemyPlayer, Position(1, 2))//threatening 01
    val enemyRook = Rook(genericEnemyPlayer, Position(2, 2))//threatening 20
    val enemyKing = King(genericEnemyPlayer, Position(7,7))
    val randomPiece = Rook(genericPlayer, Position(0, 6))

    val kingBoard = getBoard(king, List(randomPiece, enemyBishop, enemyBishop2, enemyRook, enemyKing))

    BoardCategorisation.isCheckmate(kingBoard) shouldBe true
  }
}
