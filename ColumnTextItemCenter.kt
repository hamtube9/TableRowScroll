package com.example.chartexample

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ColumnTextItemCenter(context: Context) : ConstraintLayout(context) {
    var view: View
    var title: TextView
    var subTitle: TextView


    init {
        view = inflate(context, R.layout.column_text_item_layout_center, this)
        title = view.findViewById(R.id.tvTitleColumnTextItemCenter)
        subTitle = view.findViewById(R.id.tvSubTitleColumnTextItemCenter)

    }

}