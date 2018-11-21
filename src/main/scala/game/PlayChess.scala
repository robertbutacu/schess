package game

import elements.boards.states.{BoardState, NormalState}
import elements.pieces._
import game.elements.boards.Position
import players.models.{AIPlayer, HumanPlayer, Player}
import players.{PlayerIndex, PlayerOne, PlayerTwo, Players}

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.language.postfixOps


object PlayChess {
  def play(): Unit = {
    @tailrec
    def go(boardState: BoardState): Option[BoardState] = {
      println(boardState)

      boardState.next match {
        case None => None
        case Some(b) => go(b)
      }
    }

    go(initiateGame)
  }

  def initiateGame: BoardState = {
    def instantiatePlayer(name: String, playerIndex: PlayerIndex, isBot: String): Player = {
      isBot.toLowerCase match {
        case "yes" => AIPlayer(name, playerIndex)
        case _ => HumanPlayer(name, playerIndex)
      }
    }

    def askForDetails(playerIndex: PlayerIndex): Player = {
      val name = readLine(s"Enter the name of player $playerIndex: ")
      val isBot = readLine("Is it a bot?")

      instantiatePlayer(name, playerIndex, isBot)
    }

    val p1 = askForDetails(PlayerOne)
    val p2 = askForDetails(PlayerTwo)

    val players = Players(p1, p2, 1)

    NormalState(initialBoardState(p1, p2), players, isFirstMove = true)
  }

  def initialBoardState(player1: Player, player2: Player): List[List[Piece]] = {
    def emptyRow(y: Int) = 0 to 7 map { x => EmptyPosition(Position(x, y)) } toList

    def pawnRow(player: Player, y: Int) = 0 to 7 map { x => Pawn(player, Position(x, y)) } toList

    def specialRow(player: Player, y: Int) = {
      val rook1 = Rook(player, Position(0, y))
      val rook2 = Rook(player, Position(7, y))

      val knight1 = Knight(player, Position(1, y))
      val knight2 = Knight(player, Position(6, y))

      val bishop1 = Bishop(player, Position(2, y))
      val bishop2 = Bishop(player, Position(5, y))

      val queen = Queen(player, Position(3, y))
      val king = King(player, Position(4, y))

      List(rook1, knight1, bishop1, queen, king, bishop2, knight2, rook2)
    }

    val player1SpecialRow = specialRow(player1, 0)
    val player2SpecialRow = specialRow(player2, 7)

    val player1PawnRow = pawnRow(player1, 1)
    val player2PawnRow = pawnRow(player2, 6)

    val emptyRows = 2 to 5 map emptyRow toList

    List(player1SpecialRow, player1PawnRow) ::: emptyRows ::: List(player2PawnRow, player2SpecialRow)
  }
}
