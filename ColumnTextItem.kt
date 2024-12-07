package com.example.chartexample

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ColumnTextItem(context: Context) : ConstraintLayout(context) {
    var view: View
    var title: TextView
    var subTitle: TextView
    var stockC: TextView
    var stockM: TextView
    var stockCD: TextView

    init {
        view = inflate(context, R.layout.column_text_item_layout, this)
        title = view.findViewById(R.id.tvTitleColumnTextItem)
        subTitle = view.findViewById(R.id.tvSubTitleColumnTextItem)
        stockC = view.findViewById(R.id.tvStockC)
        stockM = view.findViewById(R.id.tvStockM)
        stockCD = view.findViewById(R.id.tvStockCD)
    }

}