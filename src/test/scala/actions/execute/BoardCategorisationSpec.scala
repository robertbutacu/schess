package actions.execute

import actions.Position
import elements.pieces.{Bishop, BoardSetup, King, Rook}
import org.scalatest.{FlatSpec, Matchers}
import players.Players

class BoardCategorisationSpec extends FlatSpec with Matchers with BoardSetup {
  "When the king is in checkmate, the function" should "respond positive" in {
    val king = King(genericPlayer, Position(0, 0))
    val enemyBishop = Bishop(genericEnemyPlayer, Position(0, 2))//threatening 11
    val enemyBishop2 = Bishop(genericEnemyPlayer, Position(1, 2))//threatening 01
    val enemyRook = Rook(genericEnemyPlayer, Position(2, 2))//threatening 20
    val enemyKing = King(genericEnemyPlayer, Position(7,7))

    val kingBoard = getBoard(king, List(enemyBishop, enemyBishop2, enemyRook, enemyKing))

    BoardCategorisation.isCheckmate(kingBoard, Players(genericPlayer, genericEnemyPlayer, genericPlayer.index.toInt)) shouldBe true
  }
}
