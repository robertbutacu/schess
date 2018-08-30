package elements.pieces.moves

import actions.PiecePosition
import elements.boards.states.NoSpecialState
import elements.pieces._
import org.scalatest.FlatSpec
import players.AIPlayer
import elements.pieces.moves.MoveValidator._

class MoveValidatorSpec extends FlatSpec {
  val genericPlayer = AIPlayer("XXX", 1)
  val genericPosition = PiecePosition(4, 4)

  lazy val rook = Rook(genericPlayer, genericPosition)
  lazy val queen = Queen(genericPlayer, genericPosition)
  lazy val king = King(genericPlayer, genericPosition)
  lazy val bishop = Bishop(genericPlayer, genericPosition)
  lazy val pawn = Pawn(genericPlayer, genericPosition)
  lazy val knight = Knight(genericPlayer, genericPosition)

  lazy val board = NoSpecialState(List.empty)


  /**
    * 7   0.7    1.7   2.7   3.7   4.7   5.7   6.7   7.7
    *
    * 6   0.6    1.6   2.6   3.6   4.6   5.6   6.6   7.6
    *
    * 5   0.5    1.5   2.5   3.5   4.5   5.5   6.5   7.5
    *
    * 4   0.4    1.4   2.4   3.4   4.4   5.4   6.4   7.4
    *
    * 3   0.3    1.3   2.3   3.3   4.3   5.3   6.3   7.3
    *
    * 2   0.2    1.2   2.2   3.2   4.2   5.2   6.2   7.2
    *
    * 1   0.1    1.1   2.1   3.1   4.1   5.1   6.1   7.1
    *
    * 0   0.0    1.0   2.0   3.0   4.0   5.0   6.0   7.0
    *
    * 0      1     2     3     4     5     6     7
    */


  "Initialization with bad position" should "fail " in {
    lazy val invalidPiece1 = Pawn(genericPlayer, PiecePosition(-1, 0))  //X low
    lazy val invalidPiece2 = Pawn(genericPlayer, PiecePosition(1, -1))  //Y low
    lazy val invalidPiece3 = Pawn(genericPlayer, PiecePosition(1, 8))   //Y high
    lazy val invalidPiece4 = Pawn(genericPlayer, PiecePosition(-1, -2)) //both low
    lazy val invalidPiece5 = Pawn(genericPlayer, PiecePosition(11, 0))  //X high
    lazy val invalidPiece6 = Pawn(genericPlayer, PiecePosition(11, 11)) //both high

    assertThrows[IllegalArgumentException](invalidPiece1)
    assertThrows[IllegalArgumentException](invalidPiece2)
    assertThrows[IllegalArgumentException](invalidPiece3)
    assertThrows[IllegalArgumentException](invalidPiece4)
    assertThrows[IllegalArgumentException](invalidPiece5)
    assertThrows[IllegalArgumentException](invalidPiece6)
  }

  "A rook " should " make valid moves " in {
    assert(board.isValidMove(rook, PiecePosition(4, 7)) === true) // moving along Y axis up
    assert(board.isValidMove(rook, PiecePosition(4, 0)) === true) // moving along Y axis down
    assert(board.isValidMove(rook, PiecePosition(7, 4)) === true) // moving along X axis up
    assert(board.isValidMove(rook, PiecePosition(0, 4)) === true) // moving along X axis down
  }

  "A rook" should "not make valid moves " in {
    //TODO fix
    // assert(rook.isValidMove(PiecePosition(4, 4)) === false )// standing still
    assert(board.isValidMove(rook, PiecePosition(2, 3)) === false)
    assert(board.isValidMove(rook, PiecePosition(3, 5)) === false)
    assert(board.isValidMove(rook, PiecePosition(7, 7)) === false)
    assert(board.isValidMove(rook, PiecePosition(0, 0)) === false)
  }

  "A queen " should " make valid moves " in {

  }

  "A queen" should "not make valid moves " in {

  }

  "A bishop " should " make valid moves " in {

  }

  "A bishop" should "not make valid moves " in {

  }

  "A king " should " make valid moves " in {

  }

  "A king" should "not make valid moves " in {

  }

  "A pawn " should " make valid moves " in {

  }

  "A pawn" should "not make valid moves " in {

  }

  "A knight " should " make valid moves " in {
    assert(board.isValidMove(knight, PiecePosition(5, 6)) === true)
    assert(board.isValidMove(knight, PiecePosition(5, 2)) === true)
    assert(board.isValidMove(knight, PiecePosition(3, 2)) === true)
    assert(board.isValidMove(knight, PiecePosition(3, 6)) === true)
  }

  "A knight" should "not make valid moves " in {
    assert(board.isValidMove(knight, PiecePosition(5, 7)) === false)
    assert(board.isValidMove(knight, PiecePosition(4, 5)) === false)
    assert(board.isValidMove(knight, PiecePosition(3, 4)) === false)
    assert(board.isValidMove(knight, PiecePosition(3, 7)) === false)
  }
}
