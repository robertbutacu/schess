package actions.validators.pieces

import game.elements.pieces._
import org.scalatest._
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import game.elements.boards.states.BoardState
import validator.{Failure, Success}
import game.elements.boards.Position

class MoveValidatorSpec extends FlatSpec with Matchers with BoardSetup {
  "IsPieceOwner " should "return true on pieces of specific kind belonging to specific game.players" in {
    lazy val rookBoard = getBoard(rook)

    rookBoard.isOwningRook(rook.position, rook.player) shouldBe Success(_: BoardState)
  }

  "isOwningRook" should "return false when a player is not owning the piece which should be a rook" in {
    lazy val pawnBoard = getBoard(pawn)

    pawnBoard.isOwningRook(pawn.position, pawn.player) shouldBe Failure(_: String, _: BoardState)
  }

  "Initialization with bad position" should "fail " in {
    lazy val invalidPiece1 = Pawn(genericPlayer, Position(-1, 0)) //X low
    lazy val invalidPiece2 = Pawn(genericPlayer, Position(1, -1)) //Y low
    lazy val invalidPiece3 = Pawn(genericPlayer, Position(1, 8)) //Y high
    lazy val invalidPiece4 = Pawn(genericPlayer, Position(-1, -2)) //both low
    lazy val invalidPiece5 = Pawn(genericPlayer, Position(11, 0)) //X high
    lazy val invalidPiece6 = Pawn(genericPlayer, Position(11, 11)) //both high

    assertThrows[IllegalArgumentException](invalidPiece1)
    assertThrows[IllegalArgumentException](invalidPiece2)
    assertThrows[IllegalArgumentException](invalidPiece3)
    assertThrows[IllegalArgumentException](invalidPiece4)
    assertThrows[IllegalArgumentException](invalidPiece5)
    assertThrows[IllegalArgumentException](invalidPiece6)
  }

  "A rook " should " make valid moves " in {
    val rookBoard = getBoard(rook)
    rookBoard.isValidMove(rook, Position(4, 7)) shouldBe Success(_: BoardState) // moving along Y axis up
    rookBoard.isValidMove(rook, Position(4, 0)) shouldBe Success(_: BoardState) // moving along Y axis down
    rookBoard.isValidMove(rook, Position(7, 4)) shouldBe Success(_: BoardState) // moving along X axis up
    rookBoard.isValidMove(rook, Position(0, 4)) shouldBe Success(_: BoardState) // moving along X axis down
  }

  "A rook" should "not make valid moves " in {
    val rookBoard = getBoard(rook)
    rookBoard.isValidMove(rook, Position(2, 3)) shouldBe Failure(_: String, _: BoardState)
    rookBoard.isValidMove(rook, Position(3, 5)) shouldBe Failure(_: String, _: BoardState)
    rookBoard.isValidMove(rook, Position(7, 7)) shouldBe Failure(_: String, _: BoardState)
    rookBoard.isValidMove(rook, Position(0, 0)) shouldBe Failure(_: String, _: BoardState)
  }

  "A queen " should " make valid moves " in {
    val queenBoard = getBoard(queen)

    //rook moves
    queenBoard.isValidMove(queen, Position(4, 7)) shouldBe Success(_: BoardState) // moving along Y axis up
    queenBoard.isValidMove(queen, Position(4, 0)) shouldBe Success(_: BoardState) // moving along Y axis down
    queenBoard.isValidMove(queen, Position(7, 4)) shouldBe Success(_: BoardState) // moving along X axis up
    queenBoard.isValidMove(queen, Position(0, 4)) shouldBe Success(_: BoardState) // moving along X axis down


    //bishop moves
    queenBoard.isValidMove(queen, Position(7, 7)) shouldBe Success(_: BoardState) // right up
    queenBoard.isValidMove(queen, Position(7, 1)) shouldBe Success(_: BoardState) // right down
    queenBoard.isValidMove(queen, Position(1, 7)) shouldBe Success(_: BoardState) // left up
    queenBoard.isValidMove(queen, Position(0, 0)) shouldBe Success(_: BoardState) // left down
  }

  "A queen" should "not make valid moves " in {

  }

  "A bishop " should " make valid moves " in {
    val bishopBoard = getBoard(bishop)
    bishopBoard.isValidMove(bishop, Position(7, 7)) shouldBe Success(_: BoardState) // right up
    bishopBoard.isValidMove(bishop, Position(7, 1)) shouldBe Success(_: BoardState) // right down
    bishopBoard.isValidMove(bishop, Position(1, 7)) shouldBe Success(_: BoardState) // left up
    bishopBoard.isValidMove(bishop, Position(0, 0)) shouldBe Success(_: BoardState) // left down
  }


  "A bishop" should "not make valid moves " in {
    val bishopBoard = getBoard(bishop)
    bishopBoard.isValidMove(bishop, Position(4, 7)) shouldBe Failure(_: String, _: BoardState) // straight up
    bishopBoard.isValidMove(bishop, Position(3, 7)) shouldBe Failure(_: String, _: BoardState) // random
    bishopBoard.isValidMove(bishop, Position(4, 0)) shouldBe Failure(_: String, _: BoardState) // straight down
    bishopBoard.isValidMove(bishop, Position(4, 5)) shouldBe Failure(_: String, _: BoardState) // pawn move
  }

  "A king " should " make valid moves " in {
    val kingBoard = getBoard(king)
    kingBoard.isValidMove(king, Position(4, 5)) shouldBe Success(_: BoardState) //
    kingBoard.isValidMove(king, Position(4, 3)) shouldBe Success(_: BoardState) //
    kingBoard.isValidMove(king, Position(5, 4)) shouldBe Success(_: BoardState) //
    kingBoard.isValidMove(king, Position(5, 5)) shouldBe Success(_: BoardState) //
    kingBoard.isValidMove(king, Position(3, 3)) shouldBe Success(_: BoardState) //
    kingBoard.isValidMove(king, Position(3, 4)) shouldBe Success(_: BoardState) //
    kingBoard.isValidMove(king, Position(5, 3)) shouldBe Success(_: BoardState) //
    kingBoard.isValidMove(king, Position(3, 5)) shouldBe Success(_: BoardState) //
  }

  "A king " should "be able to castle " in {
    val kingBoard = getBoard(kingDefaultPosition, List(rookDefaultPositionLeft, rookDefaultPositionRight))

    kingBoard.isValidMove(kingDefaultPosition, Position(0, 0)) shouldBe Success(_: BoardState)
    kingBoard.isValidMove(kingDefaultPosition, Position(7, 0)) shouldBe Success(_: BoardState)
  }

  "A king " should "not be able to castle using enemy rooks" in {
    val kingBoard = getBoard(kingDefaultPosition, List(rookDefaultPositionLeftEnemy, rookDefaultPositionRightEnemy))

    kingBoard.isValidMove(kingDefaultPosition, Position(0, 0)) shouldBe Failure(_: String, _: BoardState)
    kingBoard.isValidMove(kingDefaultPosition, Position(7, 0)) shouldBe Failure(_: String, _: BoardState)
  }


  "A king" should "not make valid moves " in {

  }

  "A pawn " should " make valid moves " in {
    val pawnBoard = getBoard(pawn)
    pawnBoard.isValidMove(pawn, Position(4, 5)) shouldBe Success(_: BoardState) //
    pawnBoard.isValidMove(pawn, Position(3, 5)) shouldBe Failure(_: String, _: BoardState) // en passant left => this should be true
    pawnBoard.isValidMove(pawn, Position(5, 5)) shouldBe Failure(_: String, _: BoardState) // en passant right => this should be true
  }

  "A pawn" should "not make valid moves " in {
    val pawnBoard = getBoard(pawn, List.empty)

    pawnBoard.isValidMove(pawn, Position(6, 5)) shouldBe Failure(_: String, _: BoardState)
    pawnBoard.isValidMove(pawn, Position(3, 4)) shouldBe Failure(_: String, _: BoardState)
    pawnBoard.isValidMove(pawn, Position(5, 4)) shouldBe Failure(_: String, _: BoardState)
    pawnBoard.isValidMove(pawn, Position(4, 3)) shouldBe Failure(_: String, _: BoardState)
  }

  "A pawn" should "not be able to walk straight if position is occupied" in {
    val pawnBoard = getBoard(pawn, List(Pawn(genericEnemyPlayer, Position(4, 5))))

    pawnBoard.isValidMove(pawn, Position(4, 5)) shouldBe Failure(_: String, _: BoardState)
  }

  "A pawn" should "be able to walk straight if position is free " in {
    val pawnBoard = getBoard(pawn, List(Pawn(genericEnemyPlayer, Position(4, 5))))

    pawnBoard.isValidMove(pawn, Position(4, 5)) shouldBe Success(_: BoardState)
  }

  "A pawn " should "be able to eat a piece diagonally" in {
    val pawnBoard = getBoard(pawn, List(Pawn(genericEnemyPlayer, Position(3, 5)), Pawn(genericEnemyPlayer, Position(5, 5))))

    pawnBoard.isValidMove(pawn, Position(3, 5)) shouldBe Success(_: BoardState)
    pawnBoard.isValidMove(pawn, Position(5, 5)) shouldBe Success(_: BoardState)
  }

  "A knight " should " make valid moves " in {
    val knightBoard = getBoard(knight)

    knightBoard.isValidMove(knight, Position(5, 6)) shouldBe Success(_: BoardState)
    knightBoard.isValidMove(knight, Position(5, 2)) shouldBe Success(_: BoardState)
    knightBoard.isValidMove(knight, Position(3, 2)) shouldBe Success(_: BoardState)
    knightBoard.isValidMove(knight, Position(3, 6)) shouldBe Success(_: BoardState)
  }

  "A knight" should "not make valid moves " in {
    val knightBoard = getBoard(knight)

    knightBoard.isValidMove(knight, Position(5, 7)) shouldBe Failure(_: String, _: BoardState)
    knightBoard.isValidMove(knight, Position(4, 5)) shouldBe Failure(_: String, _: BoardState)
    knightBoard.isValidMove(knight, Position(3, 4)) shouldBe Failure(_: String, _: BoardState)
    knightBoard.isValidMove(knight, Position(3, 7)) shouldBe Failure(_: String, _: BoardState)
  }

  "Trying to move an empty position" should "not be allowed" in {
    val board = getBoard(knight, List.empty)

    board.isValidMove(board.pieces(7)(7), Position(0, 0)) shouldBe Failure(_: String, _: BoardState)
  }
}
