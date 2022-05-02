package com.example.filler.gui.configuration

import android.content.Intent
import android.widget.Toast
import com.example.filler.gui.game.GUIGame

class StartNewGame(
    private val context: NewGameConfiguration,

    username: String,
    colorNum: String,
    gridNum: String,
    timeControl: Boolean,
    difficulty: String
) {
    init {
        if (username.isEmpty()) {
            val error = "Please enter a username"
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        } else {
            // Stop the song player
            stopConfSong(context)

            val intent = buildNewGameIntent(username, colorNum, gridNum, timeControl, difficulty)
            context.startActivity(intent)
        }
    }

    private fun buildNewGameIntent(
        username: String,
        colorNum: String,
        gridNum: String,
        timeControl: Boolean,
        difficulty: String
    ): Intent {
        val intent = Intent(context, GUIGame::class.java)
        intent.putExtra("username", username)
        intent.putExtra("colorNum", getIntColorNum(colorNum))
        intent.putExtra("gridNum", getIntGridNum(gridNum))
        intent.putExtra("timeControl", timeControl)
        intent.putExtra("difficulty", difficulty)
        return intent
    }

    private fun getIntColorNum(colorNum: String): Int =
        colorNum.first().digitToInt()

    private fun getIntGridNum(gridNum: String): Int =
    // Return an integer of the first number
    // As it's square, we only needed the first one
        // 3 X 3 -> 3
        gridNum.first().digitToInt()
}
