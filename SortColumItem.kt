package com.example.chartexample

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class SortColumItem(context: Context) : ConstraintLayout(context) {
    var view: View
    var title: TextView
    var ivSort : ImageView

    init {
        view = inflate(context, R.layout.sort_colum_layout, this)
        title = view.findViewById(R.id.tvSortColum)
        ivSort = view.findViewById(R.id.ivSort)
    }

}