package elements.pieces.boards

import actions.execute.MoveCategorisation
import actions.{Move, Position}
import elements.pieces.{BoardSetup, King, Rook}
import org.scalatest.{FlatSpec, Matchers}

class BoardStateSpec extends FlatSpec with Matchers with BoardSetup {

  "Moving a king in a check position" should "not be allowed" in {
    val kingBoard = getBoard(king, List(Rook(genericEnemyPlayer, Position(5, 0)), King(genericEnemyPlayer, Position(0, 0))))

    val updatedBoard = MoveCategorisation.categorise(kingBoard, Position(4, 4), Position(5, 5))
    updatedBoard.isKingInCheck() shouldBe true
  }
}
