package elements.pieces.moves

import actions.PiecePosition
import elements.boards.states.NoSpecialState
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
    assert(rookBoard.isValidPath(rook, PiecePosition(4, 7)) === true) // moving along Y axis up
    assert(rookBoard.isValidPath(rook, PiecePosition(4, 0)) === true) // moving along Y axis down
    assert(rookBoard.isValidPath(rook, PiecePosition(7, 4)) === true) // moving along X axis up
    assert(rookBoard.isValidPath(rook, PiecePosition(0, 4)) === true) // moving along X axis down
  }

  "A rook" should "not make valid moves " in {
    val rookBoard = getBoard(rook)
    assert(rookBoard.isValidPath(rook, PiecePosition(2, 3)) === false)
    assert(rookBoard.isValidPath(rook, PiecePosition(3, 5)) === false)
    assert(rookBoard.isValidPath(rook, PiecePosition(7, 7)) === false)
    assert(rookBoard.isValidPath(rook, PiecePosition(0, 0)) === false)
  }

  "A queen " should " make valid moves " in {
    val queenBoard = getBoard(queen)

    //rook moves
    assert(queenBoard.isValidPath(queen, PiecePosition(4, 7)) === true) // moving along Y axis up
    assert(queenBoard.isValidPath(queen, PiecePosition(4, 0)) === true) // moving along Y axis down
    assert(queenBoard.isValidPath(queen, PiecePosition(7, 4)) === true) // moving along X axis up
    assert(queenBoard.isValidPath(queen, PiecePosition(0, 4)) === true) // moving along X axis down


    //bishop moves
    assert(queenBoard.isValidPath(queen, PiecePosition(7, 7)) === true) // right up
    assert(queenBoard.isValidPath(queen, PiecePosition(7, 1)) === true) // right down
    assert(queenBoard.isValidPath(queen, PiecePosition(1, 7)) === true) // left up
    assert(queenBoard.isValidPath(queen, PiecePosition(0, 0)) === true) // left down
  }

  "A queen" should "not make valid moves " in {

  }

  "A bishop " should " make valid moves " in {
    val bishopBoard = getBoard(bishop)
    assert(bishopBoard.isValidPath(bishop, PiecePosition(7, 7)) === true) // right up
    assert(bishopBoard.isValidPath(bishop, PiecePosition(7, 1)) === true) // right down
    assert(bishopBoard.isValidPath(bishop, PiecePosition(1, 7)) === true) // left up
    assert(bishopBoard.isValidPath(bishop, PiecePosition(0, 0)) === true) // left down
  }


  "A bishop" should "not make valid moves " in {
    val bishopBoard = getBoard(bishop)
    assert(bishopBoard.isValidPath(bishop, PiecePosition(4, 7)) === false) // straight up
    assert(bishopBoard.isValidPath(bishop, PiecePosition(3, 7)) === false) // random
    assert(bishopBoard.isValidPath(bishop, PiecePosition(4, 0)) === false) // straight down
    assert(bishopBoard.isValidPath(bishop, PiecePosition(4, 5)) === false) // pawn move
  }

  "A king " should " make valid moves " in {
    val kingBoard = getBoard(king)
    assert(kingBoard.isValidPath(king, PiecePosition(4, 5)) === true) //
    assert(kingBoard.isValidPath(king, PiecePosition(4, 3)) === true) //
    assert(kingBoard.isValidPath(king, PiecePosition(5, 4)) === true) //
    assert(kingBoard.isValidPath(king, PiecePosition(5, 5)) === true) //
    assert(kingBoard.isValidPath(king, PiecePosition(3, 3)) === true) //
    assert(kingBoard.isValidPath(king, PiecePosition(3, 4)) === true) //
    assert(kingBoard.isValidPath(king, PiecePosition(5, 3)) === true) //
    assert(kingBoard.isValidPath(king, PiecePosition(3, 5)) === true) //
  }

  "A king" should "not make valid moves " in {

  }

  "A pawn " should " make valid moves " in {
    val pawnBoard = getBoard(pawn)
    assert(pawnBoard.isValidPath(pawn, PiecePosition(4, 5)) === true) //
    assert(pawnBoard.isValidPath(pawn, PiecePosition(3, 5)) === true) //
    assert(pawnBoard.isValidPath(pawn, PiecePosition(5, 5)) === true) //
  }

  "A pawn" should "not make valid moves " in {

  }

  "A knight " should " make valid moves " in {
    assert(board.isValidPath(knight, PiecePosition(5, 6)) === true)
    assert(board.isValidPath(knight, PiecePosition(5, 2)) === true)
    assert(board.isValidPath(knight, PiecePosition(3, 2)) === true)
    assert(board.isValidPath(knight, PiecePosition(3, 6)) === true)
  }

  "A knight" should "not make valid moves " in {
    assert(board.isValidPath(knight, PiecePosition(5, 7)) === false)
    assert(board.isValidPath(knight, PiecePosition(4, 5)) === false)
    assert(board.isValidPath(knight, PiecePosition(3, 4)) === false)
    assert(board.isValidPath(knight, PiecePosition(3, 7)) === false)
  }
}
