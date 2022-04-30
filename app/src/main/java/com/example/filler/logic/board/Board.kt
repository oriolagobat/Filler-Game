package com.example.filler.logic.board

import com.example.filler.constants.GameColor
import com.example.filler.logic.game.Position

interface Board {
    val width: Int
    val colors: MutableList<GameColor>
    fun getP1Home(): Position
    fun getP2Home(): Position
    fun getColor(position: Position): GameColor
    fun setColor(position: Position, color: GameColor)
    fun toArray(): Array<GameColor>
    fun hasPosition(position: Position): Boolean
}