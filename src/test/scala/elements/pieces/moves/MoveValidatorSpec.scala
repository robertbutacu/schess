package elements.pieces.moves

import actions.PiecePosition
import elements.pieces._
import org.scalatest._
import elements.pieces.moves.MoveValidator._

class MoveValidatorSpec extends FlatSpec with Matchers with Board {
  "Initialization with bad position" should "fail " in {
    lazy val invalidPiece1 = Pawn(genericPlayer, PiecePosition(-1, 0)) //X low
    lazy val invalidPiece2 = Pawn(genericPlayer, PiecePosition(1, -1)) //Y low
    lazy val invalidPiece3 = Pawn(genericPlayer, PiecePosition(1, 8)) //Y high
    lazy val invalidPiece4 = Pawn(genericPlayer, PiecePosition(-1, -2)) //both low
    lazy val invalidPiece5 = Pawn(genericPlayer, PiecePosition(11, 0)) //X high
    lazy val invalidPiece6 = Pawn(genericPlayer, PiecePosition(11, 11)) //both high

    assertThrows[IllegalArgumentException](invalidPiece1)
    assertThrows[IllegalArgumentException](invalidPiece2)
    assertThrows[IllegalArgumentException](invalidPiece3)
    assertThrows[IllegalArgumentException](invalidPiece4)
    assertThrows[IllegalArgumentException](invalidPiece5)
    assertThrows[IllegalArgumentException](invalidPiece6)
  }

  "A rook " should " make valid moves " in {
    val rookBoard = getBoard(rook)
    rookBoard.isValidMove(rook, PiecePosition(4, 7)) shouldBe true // moving along Y axis up
    rookBoard.isValidMove(rook, PiecePosition(4, 0)) shouldBe true // moving along Y axis down
    rookBoard.isValidMove(rook, PiecePosition(7, 4)) shouldBe true // moving along X axis up
    rookBoard.isValidMove(rook, PiecePosition(0, 4)) shouldBe true // moving along X axis down
  }

  "A rook" should "not make valid moves " in {
    val rookBoard = getBoard(rook)
    rookBoard.isValidMove(rook, PiecePosition(2, 3)) shouldBe false
    rookBoard.isValidMove(rook, PiecePosition(3, 5)) shouldBe false
    rookBoard.isValidMove(rook, PiecePosition(7, 7)) shouldBe false
    rookBoard.isValidMove(rook, PiecePosition(0, 0)) shouldBe false
  }

  "A queen " should " make valid moves " in {
    val queenBoard = getBoard(queen)

    //rook moves
    queenBoard.isValidMove(queen, PiecePosition(4, 7)) shouldBe true // moving along Y axis up
    queenBoard.isValidMove(queen, PiecePosition(4, 0)) shouldBe true // moving along Y axis down
    queenBoard.isValidMove(queen, PiecePosition(7, 4)) shouldBe true // moving along X axis up
    queenBoard.isValidMove(queen, PiecePosition(0, 4)) shouldBe true // moving along X axis down


    //bishop moves
    queenBoard.isValidMove(queen, PiecePosition(7, 7)) shouldBe true // right up
    queenBoard.isValidMove(queen, PiecePosition(7, 1)) shouldBe true // right down
    queenBoard.isValidMove(queen, PiecePosition(1, 7)) shouldBe true // left up
    queenBoard.isValidMove(queen, PiecePosition(0, 0)) shouldBe true // left down
  }

  "A queen" should "not make valid moves " in {

  }

  "A bishop " should " make valid moves " in {
    val bishopBoard = getBoard(bishop)
    bishopBoard.isValidMove(bishop, PiecePosition(7, 7)) shouldBe true // right up
    bishopBoard.isValidMove(bishop, PiecePosition(7, 1)) shouldBe true // right down
    bishopBoard.isValidMove(bishop, PiecePosition(1, 7)) shouldBe true // left up
    bishopBoard.isValidMove(bishop, PiecePosition(0, 0)) shouldBe true // left down
  }


  "A bishop" should "not make valid moves " in {
    val bishopBoard = getBoard(bishop)
    bishopBoard.isValidMove(bishop, PiecePosition(4, 7)) shouldBe false // straight up
    bishopBoard.isValidMove(bishop, PiecePosition(3, 7)) shouldBe false // random
    bishopBoard.isValidMove(bishop, PiecePosition(4, 0)) shouldBe false // straight down
    bishopBoard.isValidMove(bishop, PiecePosition(4, 5)) shouldBe false // pawn move
  }

  "A king " should " make valid moves " in {
    val kingBoard = getBoard(king)
    kingBoard.isValidMove(king, PiecePosition(4, 5)) shouldBe true //
    kingBoard.isValidMove(king, PiecePosition(4, 3)) shouldBe true //
    kingBoard.isValidMove(king, PiecePosition(5, 4)) shouldBe true //
    kingBoard.isValidMove(king, PiecePosition(5, 5)) shouldBe true //
    kingBoard.isValidMove(king, PiecePosition(3, 3)) shouldBe true //
    kingBoard.isValidMove(king, PiecePosition(3, 4)) shouldBe true //
    kingBoard.isValidMove(king, PiecePosition(5, 3)) shouldBe true //
    kingBoard.isValidMove(king, PiecePosition(3, 5)) shouldBe true //
  }

  "A king " should "be able to castle " in {
    val kingBoard = getBoard(kingDefaultPosition, List(rookDefaultPositionLeft, rookDefaultPositionRight))

    kingBoard.isValidMove(kingDefaultPosition, PiecePosition(0, 0)) shouldBe true
    kingBoard.isValidMove(kingDefaultPosition, PiecePosition(7, 0)) shouldBe true
  }

  "A king" should "not make valid moves " in {

  }

  "A pawn " should " make valid moves " in {
    val pawnBoard = getBoard(pawn)
    pawnBoard.isValidMove(pawn, PiecePosition(4, 5)) shouldBe true //
    pawnBoard.isValidMove(pawn, PiecePosition(3, 5)) shouldBe true //
    pawnBoard.isValidMove(pawn, PiecePosition(5, 5)) shouldBe true //
  }

  "A pawn" should "not make valid moves " in {

  }

  "A knight " should " make valid moves " in {
    val knightBoard = getBoard(knight)

    knightBoard.isValidMove(knight, PiecePosition(5, 6)) shouldBe true
    knightBoard.isValidMove(knight, PiecePosition(5, 2)) shouldBe true
    knightBoard.isValidMove(knight, PiecePosition(3, 2)) shouldBe true
    knightBoard.isValidMove(knight, PiecePosition(3, 6)) shouldBe true
  }

  "A knight" should "not make valid moves " in {
    val knightBoard = getBoard(knight)

    knightBoard.isValidMove(knight, PiecePosition(5, 7)) shouldBe false
    knightBoard.isValidMove(knight, PiecePosition(4, 5)) shouldBe false
    knightBoard.isValidMove(knight, PiecePosition(3, 4)) shouldBe false
    knightBoard.isValidMove(knight, PiecePosition(3, 7)) shouldBe false
  }
}
