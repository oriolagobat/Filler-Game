package com.example.filler.gui.game

import android.app.ActionBar
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.example.filler.R
import com.example.filler.constants.GameColor
import com.example.filler.databinding.ActivityGameBinding
import kotlin.math.sqrt

class GridAdapter(private val context: Context, private val colorsList: Array<GameColor>, private val binding: ActivityGameBinding) :
    BaseAdapter() {
    private var squareSize: Int = setSquareSizeFromTotalNumber()
    override fun getCount(): Int {
        return colorsList.size
    }

    override fun getItem(position: Int): GameColor {
        return colorsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Inflate the layout if it is not already inflated
        val view: View = convertView ?: View.inflate(context, R.layout.grid_item, null)
        // Get the textview from the layout
        val textView = view.findViewById<TextView>(R.id.gridItem)

        binding.boardGridView.layoutParams.width = 900
        binding.boardGridView.layoutParams.height = 900

        // Set the textView size
        setSize(textView)

        // Get the background color from the corresponding array position and set it
        val drawableColorId = getColorFromGameColor(getItem(position))
        val background: Drawable = AppCompatResources.getDrawable(context, drawableColorId)!!
        textView.background = background

        return view
    }

    private fun setSquareSizeFromTotalNumber(): Int {
        val totalNumber = colorsList.size
        val squaresPerLine = sqrt(totalNumber.toDouble()).toInt()
        // Perfect relation is 3:100, so we just apply this relation to the number of squares
        return (3 * 100) / squaresPerLine
    }

    private fun setSize(textView: TextView) {
        // FIXME: Developing
//        val layoutParams = LinearLayout.LayoutParams(squareSize, squareSize)

//        textView.layoutParams = layoutParams
        textView.width = squareSize * 3
        textView.height = squareSize * 3
    }

    private fun getColorFromGameColor(gameColor: GameColor): Int {
        return when (gameColor) {
            GameColor.PINK -> R.color.game_pink
            GameColor.ORANGE -> R.color.game_orange
            GameColor.YELLOW -> R.color.game_yellow
            GameColor.GREEN -> R.color.game_green
            GameColor.BLUE -> R.color.game_blue
            GameColor.PURPLE -> R.color.game_purple
            GameColor.CYAN -> R.color.game_cyan
            GameColor.BLACK -> R.color.black
            else -> throw UnsupportedOperationException("No more colors...")
        }
    }
}