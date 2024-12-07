package com.example.chartexample

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ColumnTextValueChange(context: Context) : ConstraintLayout(context) {
    var view: View
    var title: TextView
    var subTitle: TextView
    var ivValueChange : ImageView

    init {
        view = inflate(context, R.layout.column_text_value_change_layout, this)
        title = view.findViewById(R.id.tvTitleColumnTextValueChange)
        subTitle = view.findViewById(R.id.tvSubTitleColumnTextValueChange)
       ivValueChange = view.findViewById(R.id.ivValueChange)
    }

}