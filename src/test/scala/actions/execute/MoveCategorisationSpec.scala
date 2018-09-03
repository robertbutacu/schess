package actions.execute

import actions.Position
import elements.pieces.{King, Rook}
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

  "Castling the king" should "correctly return the board with the king castled left side" in {
    val kingBoard = getBoard(King(genericPlayer, Position(4, 0)), List(Rook(genericPlayer, Position(0, 0))))

    lazy val expected = getBoard(King(genericPlayer, Position(2, 0)), List(Rook(genericPlayer, Position(3, 0))))

    val actual = MoveCategorisation.categorise(kingBoard, Position(4, 0), Position(0 ,0))

    actual shouldBe expected
  }

  "Castling the king" should "correctly return the board with the king castled right side" in {
    val kingBoard = getBoard(King(genericPlayer, Position(4, 0)), List(Rook(genericPlayer, Position(7, 0))))

    lazy val expected = getBoard(King(genericPlayer, Position(6, 0)), List(Rook(genericPlayer, Position(5, 0))))

    val actual = MoveCategorisation.categorise(kingBoard, Position(4, 0), Position(7 ,0))

    actual shouldBe expected
  }
}
