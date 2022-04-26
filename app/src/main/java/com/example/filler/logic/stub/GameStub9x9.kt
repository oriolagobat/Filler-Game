package com.example.filler.logic.stub

import com.example.filler.constants.Difficulty
import com.example.filler.constants.GameColor
import com.example.filler.constants.GameState
import com.example.filler.logic.*

class GameStub9x9(private val settings: GameSettings) : Game {
    private val boardSize = 9
    private val colorArray = arrayListOf(
        GameColor.ORANGE, GameColor.ORANGE, GameColor.ORANGE, GameColor.ORANGE, GameColor.ORANGE, GameColor.ORANGE, GameColor.ORANGE, GameColor.ORANGE, GameColor.ORANGE,
        GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK,
        GameColor.CYAN, GameColor.CYAN, GameColor.CYAN, GameColor.CYAN, GameColor.CYAN, GameColor.CYAN, GameColor.CYAN, GameColor.CYAN, GameColor.CYAN,
        GameColor.YELLOW, GameColor.YELLOW, GameColor.YELLOW, GameColor.YELLOW, GameColor.YELLOW, GameColor.YELLOW, GameColor.YELLOW, GameColor.YELLOW, GameColor.YELLOW,
        GameColor.BLUE, GameColor.BLUE, GameColor.BLUE, GameColor.BLUE, GameColor.BLUE, GameColor.BLUE, GameColor.BLUE, GameColor.BLUE, GameColor.BLUE,
        GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK, GameColor.BLACK,
        GameColor.PINK, GameColor.PINK, GameColor.PINK, GameColor.PINK, GameColor.PINK, GameColor.PINK, GameColor.PINK, GameColor.PINK, GameColor.PINK,
        GameColor.GREEN, GameColor.GREEN, GameColor.GREEN, GameColor.GREEN, GameColor.GREEN, GameColor.GREEN, GameColor.GREEN, GameColor.GREEN, GameColor.GREEN,
        GameColor.PURPLE, GameColor.PURPLE, GameColor.PURPLE, GameColor.PURPLE, GameColor.PURPLE, GameColor.PURPLE, GameColor.PURPLE, GameColor.PURPLE, GameColor.PURPLE,
    )

    private val board: Board = BoardImpl(boardSize)
    private val selector: ColorSelector = ColorSelectorImpl(
        colorArray,
        board.getColor(board.getP1Home()),
        board.getColor(board.getP2Home())
    )

    private val response = GameResponse(
        0,
        board,
        selector,
        GameState.P1_TURN
    )

    init {
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                board.setColor(Position(row, col), colorArray[row * boardSize + col])
            }
        }
    }

    override fun initGame(): GameResponse {
        return response
    }

    override fun startGame() {
        print("Game started")
    }

    override fun pickColor(color: GameColor): GameResponse {
        return response
    }
}