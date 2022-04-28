package com.example.filler.gui.game

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.example.filler.R
import com.example.filler.constants.GameColor

class SelectorAdapter(
    private val context: Context,
    private val content: Array<Pair<GameColor, Boolean>>,
    private val grid: GridView
) : BaseAdapter() {
    override fun getCount(): Int {
        return content.size
    }

    override fun getItem(position: Int): Pair<GameColor, Boolean> {
        return content[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Inflate the layout if it is not already inflated
        val view: View = convertView ?: View.inflate(context, R.layout.board_item, null)
        // Get the textview from the layout
        val textView = view.findViewById<TextView>(R.id.boardItem)

        // Get the background color from the corresponding array position and set it
        val drawableColorId = getColorFromGameColor(getItem(position).first)
        val background: Drawable = AppCompatResources.getDrawable(context, drawableColorId)!!
        textView.background = background
        setTextViewHeight(textView)

        // If color is un-clickable apply an alpha to it
        applyAlphaIfUnClickable(position, textView)

        return view
    }

    private fun applyAlphaIfUnClickable(position: Int, textView: TextView) {
        if (getItem(position).second) {
            textView.alpha = 0.1f
        }
    }

    // Textview height should be the total grid's width divided by the number of columns (square per row)
    private fun setTextViewHeight(textView: TextView) {
        textView.layoutParams.height = grid.width / getSquarePerRow()
    }


    // We'll have as many squares in a row as the total number of squares.
    private fun getSquarePerRow(): Int =
        content.size
}