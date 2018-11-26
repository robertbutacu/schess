package actions.execute

import game.elements.boards.Position
import game.elements.pieces._
import org.scalatest.{FlatSpec, Matchers}
import actions.validators.board.BoardQueries.BoardQueriesImplicit

class BoardCategorisationSpec extends FlatSpec with Matchers with BoardSetup {
  "When the king is in not in checkmate, the function" should "respond accordingly" in {
    val king = King(genericPlayer, Position(0, 0))
    val enemyQueen = Queen(genericEnemyPlayer, Position(1, 2))//threatening 01, 10, 11
    val enemyKing = King(genericEnemyPlayer, Position(7,7))
    val randomPiece = Rook(genericPlayer, Position(0, 6))

    val kingBoard = getBoard(king, List(randomPiece, enemyQueen, enemyKing))

    kingBoard.isStalemate shouldBe false
    kingBoard.isCheckmate shouldBe false
    kingBoard.isCheck     shouldBe false
  }

  "When there is a stalemate, the function" should "respond positive" in {
    // A stalemate is caracterized by the impossibility of the current player to make
    // a move without going into check.
    val king = King(genericPlayer, Position(0,0))
    val enemyKing = King(genericEnemyPlayer, Position(7,7))
    val enemyRook = Rook(genericEnemyPlayer, Position(1, 3)) //threatening 11, 01
    val enemyRook2 = Rook(genericEnemyPlayer, Position(5, 1)) //threatening 11, 10

    val kingBoard = getBoard(king, List(enemyRook, enemyRook2, enemyKing))

    kingBoard.isStalemate shouldBe true
  }

  "When the king is check, the function isCheck" should "respond positive" in {
    val king       = King(genericPlayer, Position(0,0))
    val enemyKing  = King(genericEnemyPlayer, Position(7,7))
    val enemyRook  = Rook(genericEnemyPlayer, Position(0, 7)) //threatening 11, 01

    val kingBoard = getBoard(king, List(enemyRook, enemyKing))

    kingBoard.isCheck shouldBe true
  }

  "When there is another piece protecting the king from checkmate, the function isCheckmate" should "respond positive" in {
    val king       = King(genericPlayer, Position(0,0))
    val enemyKing  = King(genericEnemyPlayer, Position(7,7))
    val enemyRook  = Rook(genericEnemyPlayer, Position(0, 7)) //threatening 11, 01
    val enemyRook2 = Rook(genericEnemyPlayer, Position(1, 7)) //threatening 11, 10
    val allyRook   = Rook(genericPlayer, Position(5, 5))

    val kingBoard = getBoard(king, List(allyRook, enemyRook, enemyRook2, enemyKing))

    //kingBoard.areThereOtherMovesAvailable() shouldBe true
    kingBoard.isCheckmate shouldBe false
  }

  "When the king is in check and his new positions are in threat" should "be checkmate" in {
    val king       = King(genericPlayer, Position(0,0))
    val enemyKing  = King(genericEnemyPlayer, Position(7,7))
    val enemyRook  = Rook(genericEnemyPlayer, Position(0, 7)) //threatening 11, 01
    val enemyRook2 = Rook(genericEnemyPlayer, Position(1, 7)) //threatening 11, 10

    val kingBoard = getBoard(king, List(enemyRook, enemyRook2, enemyKing))

    kingBoard.isCheckmate shouldBe true
  }
}
