package com.example.chartexample

import android.content.Context
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class RangeItem(context: Context) : ConstraintLayout(context) {
    var view: View
    var textLeft: TextView
    var textRight: TextView
    var seekBar: SeekBar

    init {
        view = inflate(context, R.layout.range_item_layout, this)
        textLeft = view.findViewById(R.id.tvRangeItemLeft)
        textRight = view.findViewById(R.id.tvRangeItemRight)
        seekBar = view.findViewById(R.id.sbRange)
        seekBar.setOnTouchListener { v, event -> true }
    }

    fun setRange(maxValue: Int,value : Int){
        seekBar.max = maxValue
        seekBar.progress = value
    }
}