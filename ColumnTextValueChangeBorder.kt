package com.example.chartexample

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ColumnTextValueChangeBorder(context: Context) : ConstraintLayout(context) {
    var view: View
    var title: TextView
    var subTitle: TextView

    init {
        view = inflate(context, R.layout.column_text_value_change_border_layout, this)
        title = view.findViewById(R.id.tvTitleColumnTextValueChangeBorder)
        subTitle = view.findViewById(R.id.tvSubTitleColumnTextValueChangeBorder)
    }

}