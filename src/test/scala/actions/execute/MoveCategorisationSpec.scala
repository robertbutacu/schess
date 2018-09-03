package actions.execute

import actions.Position
import elements.pieces.Rook
import elements.pieces.moves.BoardSetup
import org.scalatest.{FlatSpec, Matchers}

class MoveCategorisationSpec extends FlatSpec with Matchers with BoardSetup {
  "Moving a piece " should "correctly return the board modified" in {
    val rookBoard = getBoard(rook)
    lazy val toPositon = Position(7, 4)

    lazy val updatedRookPosition = Rook(genericPlayer, toPositon)
    val state = MoveCategorisation.categorise(rookBoard, rook.position, toPositon)

    val updatedBoard = getBoard(updatedRookPosition)

    state shouldBe updatedBoard
  }

}
