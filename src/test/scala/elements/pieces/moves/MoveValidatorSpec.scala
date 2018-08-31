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
    assert(rookBoard.isValidMove(rook, PiecePosition(4, 7)) === true) // moving along Y axis up
    assert(rookBoard.isValidMove(rook, PiecePosition(4, 0)) === true) // moving along Y axis down
    assert(rookBoard.isValidMove(rook, PiecePosition(7, 4)) === true) // moving along X axis up
    assert(rookBoard.isValidMove(rook, PiecePosition(0, 4)) === true) // moving along X axis down
  }

  "A rook" should "not make valid moves " in {
    val rookBoard = getBoard(rook)
    assert(rookBoard.isValidMove(rook, PiecePosition(2, 3)) === false)
    assert(rookBoard.isValidMove(rook, PiecePosition(3, 5)) === false)
    assert(rookBoard.isValidMove(rook, PiecePosition(7, 7)) === false)
    assert(rookBoard.isValidMove(rook, PiecePosition(0, 0)) === false)
  }

  "A queen " should " make valid moves " in {
    val queenBoard = getBoard(queen)

    //rook moves
    assert(queenBoard.isValidMove(queen, PiecePosition(4, 7)) === true) // moving along Y axis up
    assert(queenBoard.isValidMove(queen, PiecePosition(4, 0)) === true) // moving along Y axis down
    assert(queenBoard.isValidMove(queen, PiecePosition(7, 4)) === true) // moving along X axis up
    assert(queenBoard.isValidMove(queen, PiecePosition(0, 4)) === true) // moving along X axis down


    //bishop moves
    assert(queenBoard.isValidMove(queen, PiecePosition(7, 7)) === true) // right up
    assert(queenBoard.isValidMove(queen, PiecePosition(7, 1)) === true) // right down
    assert(queenBoard.isValidMove(queen, PiecePosition(1, 7)) === true) // left up
    assert(queenBoard.isValidMove(queen, PiecePosition(0, 0)) === true) // left down
  }

  "A queen" should "not make valid moves " in {

  }

  "A bishop " should " make valid moves " in {
    val bishopBoard = getBoard(bishop)
    assert(bishopBoard.isValidMove(bishop, PiecePosition(7, 7)) === true) // right up
    assert(bishopBoard.isValidMove(bishop, PiecePosition(7, 1)) === true) // right down
    assert(bishopBoard.isValidMove(bishop, PiecePosition(1, 7)) === true) // left up
    assert(bishopBoard.isValidMove(bishop, PiecePosition(0, 0)) === true) // left down
  }


  "A bishop" should "not make valid moves " in {
    val bishopBoard = getBoard(bishop)
    assert(bishopBoard.isValidMove(bishop, PiecePosition(4, 7)) === false) // straight up
    assert(bishopBoard.isValidMove(bishop, PiecePosition(3, 7)) === false) // random
    assert(bishopBoard.isValidMove(bishop, PiecePosition(4, 0)) === false) // straight down
    assert(bishopBoard.isValidMove(bishop, PiecePosition(4, 5)) === false) // pawn move
  }

  "A king " should " make valid moves " in {
    val kingBoard = getBoard(king)
    assert(kingBoard.isValidMove(king, PiecePosition(4, 5)) === true) //
    assert(kingBoard.isValidMove(king, PiecePosition(4, 3)) === true) //
    assert(kingBoard.isValidMove(king, PiecePosition(5, 4)) === true) //
    assert(kingBoard.isValidMove(king, PiecePosition(5, 5)) === true) //
    assert(kingBoard.isValidMove(king, PiecePosition(3, 3)) === true) //
    assert(kingBoard.isValidMove(king, PiecePosition(3, 4)) === true) //
    assert(kingBoard.isValidMove(king, PiecePosition(5, 3)) === true) //
    assert(kingBoard.isValidMove(king, PiecePosition(3, 5)) === true) //
  }

  "A king" should "not make valid moves " in {

  }

  "A pawn " should " make valid moves " in {
    val pawnBoard = getBoard(pawn)
    assert(pawnBoard.isValidMove(pawn, PiecePosition(4, 5)) === true) //
    assert(pawnBoard.isValidMove(pawn, PiecePosition(3, 5)) === true) //
    assert(pawnBoard.isValidMove(pawn, PiecePosition(5, 5)) === true) //
  }

  "A pawn" should "not make valid moves " in {

  }

  "A knight " should " make valid moves " in {
    val knightBoard = getBoard(knight)

    assert(knightBoard.isValidMove(knight, PiecePosition(5, 6)) === true)
    assert(knightBoard.isValidMove(knight, PiecePosition(5, 2)) === true)
    assert(knightBoard.isValidMove(knight, PiecePosition(3, 2)) === true)
    assert(knightBoard.isValidMove(knight, PiecePosition(3, 6)) === true)
  }

  "A knight" should "not make valid moves " in {
    val knightBoard = getBoard(knight)

    assert(knightBoard.isValidMove(knight, PiecePosition(5, 7)) === false)
    assert(knightBoard.isValidMove(knight, PiecePosition(4, 5)) === false)
    assert(knightBoard.isValidMove(knight, PiecePosition(3, 4)) === false)
    assert(knightBoard.isValidMove(knight, PiecePosition(3, 7)) === false)
  }
}
