package com.example.chartexample

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class TableMainLayout(context: Context) : RelativeLayout(context) {

    // set the header titles
    private var headers: Array<String> = arrayOf(
        "Code",
        "Name",
        "Last",
        "Change",
        "Total vol",
        "Bid",
        "Ask",
        "Open\nClose",
        "Day range",
        "52 wk range"
    )

    private var widthColumn = arrayOf(
        280,
        200,
        180,
        200,
        200,
        200,
        180,
        350,
        350
    )

    private var tableA: TableLayout? = null
    private var tableB: TableLayout? = null
    private var tableC: TableLayout? = null
    private var tableD: TableLayout? = null

    private var horizontalScrollViewB: HorizontalScrollView? = null
    private var horizontalScrollViewD: HorizontalScrollView? = null

    private var scrollViewC: ScrollView? = null
    private var scrollViewD: ScrollView? = null
    private var data: List<MarketModel>? = null

    var headerCellsWidth: IntArray = IntArray(headers.size)

    init {
        // initialize the main components (TableLayouts, HorizontalScrollView, ScrollView)
        this.initComponents()
        this.setComponentsId()
        this.setScrollViewAndHorizontalScrollViewTag()


        // no need to assemble component A, since it is just a table
        horizontalScrollViewB!!.addView(this.tableB)
        horizontalScrollViewB!!.isHorizontalScrollBarEnabled = false
        scrollViewC!!.addView(this.tableC)
        scrollViewC!!.isVerticalScrollBarEnabled = false
        scrollViewD!!.addView(this.horizontalScrollViewD)
        scrollViewD!!.isVerticalScrollBarEnabled = false

        horizontalScrollViewD!!.addView(this.tableD)
        horizontalScrollViewD!!.isHorizontalScrollBarEnabled = false


        // add the components to be part of the main layout
        this.addComponentToMainLayout()
        this.setBackgroundColor(Color.TRANSPARENT)


        // add some table rows
        this.addTableRowToTableA()
        this.addTableRowToTableB()

        this.resizeHeaderHeight()

        this.tableRowHeaderCellWidth


        this.resizeBodyTableRowHeight()
    }

    fun setDataMarket(newData: List<MarketModel>) {
        data = newData
        this.generateTableC_AndTable_D()
    }



    // initalized components
    private fun initComponents() {
        this.tableA = TableLayout(this.context)
        tableA!!.setBackgroundColor(Color.LTGRAY)
        this.tableB = TableLayout(this.context)
        tableB!!.showDividers = LinearLayout.SHOW_DIVIDER_NONE
        this.tableC = TableLayout(this.context)
        this.tableD = TableLayout(this.context)
        tableD!!.showDividers = LinearLayout.SHOW_DIVIDER_NONE
        tableD!!.dividerPadding = 0
        this.horizontalScrollViewB = MyHorizontalScrollView(this.context)
        this.horizontalScrollViewD = MyHorizontalScrollView(this.context)

        this.scrollViewC = MyScrollView(this.context)
        this.scrollViewD = MyScrollView(this.context)
        horizontalScrollViewB!!.setBackgroundColor(Color.parseColor("#f2f5f6"))
        horizontalScrollViewD!!.setBackgroundColor(Color.WHITE)
    }

    // set essential component IDs
    private fun setComponentsId() {
        1.also { tableA!!.id = it }
        2.also { horizontalScrollViewB!!.id = it }
        3.also { scrollViewC!!.id = it }
        4.also { scrollViewD!!.id = it }
    }

    // set tags for some horizontal and vertical scroll view
    private fun setScrollViewAndHorizontalScrollViewTag() {
        horizontalScrollViewB!!.tag = "horizontal scroll view b"
        horizontalScrollViewD!!.tag = "horizontal scroll view d"

        scrollViewC!!.tag = "scroll view c"
        scrollViewD!!.tag = "scroll view d"
    }

    // we add the components here in our TableMainLayout
    private fun addComponentToMainLayout() {
        // RelativeLayout params were very useful here
        // the addRule method is the key to arrange the components properly

        val componentB_Params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        componentB_Params.addRule(RIGHT_OF, tableA!!.id)

        val componentC_Params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        componentC_Params.addRule(BELOW, tableA!!.id)

        val componentD_Params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        componentD_Params.addRule(RIGHT_OF, scrollViewC!!.id)
        componentD_Params.addRule(BELOW, horizontalScrollViewB!!.id)


        // 'this' is a relative layout,
        // we extend this table layout as relative layout as seen during the creation of this class
        this.addView(this.tableA)
        this.addView(this.horizontalScrollViewB, componentB_Params)
        this.addView(this.scrollViewC, componentC_Params)
        this.addView(this.scrollViewD, componentD_Params)
    }


    private fun addTableRowToTableA() {
        tableA!!.addView(this.componentATableRow())
    }

    private fun addTableRowToTableB() {
        tableB!!.addView(this.componentBTableRow())
    }

    // generate table row of table A
    private fun componentATableRow(): TableRow {
        val params = TableRow.LayoutParams(headerCellsWidth[0], LayoutParams.MATCH_PARENT)
        params.setMargins(0, 0, 1, 1)
        params.width = 160

        val sortColumItem = SortColumItem(this.context)
        sortColumItem.title = this.headerTextView(headers[0])
        sortColumItem.ivSort.setImageResource(R.drawable.sort)
        sortColumItem.setBackgroundColor(Color.parseColor("#f2f5f6"))
        val componentATableRow = TableRow(this.context)
        componentATableRow.addView(sortColumItem,params)

        return componentATableRow
    }

    // generate table row of table B
    private fun componentBTableRow(): TableRow {
        val componentBTableRow = TableRow(this.context)
        val headerFieldCount = headers.size


        for (x in 1 until headerFieldCount) {
            val params = TableRow.LayoutParams(widthColumn[x - 1], LayoutParams.MATCH_PARENT)
            params.setMargins(2, 0, 0, 0)

            val textView = this.headerTextView(headers[x])
            textView.layoutParams = params
            textView.setBackgroundColor(Color.TRANSPARENT)
            componentBTableRow.addView(textView)
        }

        return componentBTableRow
    }

    // generate table row of table C and table D
    private fun generateTableC_AndTable_D() {
        // just seeing some header cell width
        if (data == null) {
            return
        }
        tableC!!.removeAllViews()
        tableD!!.removeAllViews()
        for (x in 0..this.data!!.size - 1) {
            val tableRowForTableC = this.tableRowForTableC(data!![x])
            val taleRowForTableD = this.taleRowForTableD(data!![x])
            tableRowForTableC.setBackgroundColor(Color.LTGRAY)
            taleRowForTableD.setBackgroundColor(Color.LTGRAY)

            tableC!!.addView(tableRowForTableC)
            tableD!!.addView(taleRowForTableD)
        }
    }

    // a TableRow for table C
    private fun tableRowForTableC(sampleObject: MarketModel): TableRow {
        val params = TableRow.LayoutParams(headerCellsWidth[0], LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, 1, 1)
        params.width = 160
        val tableRowForTableC = TableRow(this.context)
        val llContent = columnTextCenterAlignment(
            sampleObject.code.code, sampleObject.code.id
        )
        tableRowForTableC.addView(llContent, params)

        return tableRowForTableC
    }


    private fun columnTextEndAlignment(
        title: String?,
        subTitle: String?,
    ): ConstraintLayout {
        val columnTextItem = ColumnTextItemEnd(this.context)
        columnTextItem.title.text = title
        columnTextItem.subTitle.text = subTitle
        return columnTextItem
    }

    private fun columnTextCenterAlignment(
        title: String?,
        subTitle: String?,
    ): ConstraintLayout {
        val columnTextItem = ColumnTextItemCenter(this.context)
        columnTextItem.title.text = title
        columnTextItem.subTitle.text = subTitle
        return columnTextItem
    }


    private fun columnText(
        title: String?,
        subTitle: String?,
        stock: Array<String>?,
    ): ConstraintLayout {
        val columnTextItem = ColumnTextItem(this.context)
        columnTextItem.title.text = title
        columnTextItem.subTitle.text = subTitle

        if (stock != null) {
            if (stock.contains("C")) {
                columnTextItem.stockC.visibility = View.VISIBLE
            }
            if (stock.contains("M")) {
                columnTextItem.stockM.visibility = View.VISIBLE
            }
            if (stock.contains("CD")) {
                columnTextItem.stockCD.visibility = View.VISIBLE
            }
        }
        return columnTextItem
    }

    private fun columnTextValueChange(
        title: String?,
        subTitle: String?,
        isIncrease: Boolean
    ): ConstraintLayout {
        val columnTextValueChange = ColumnTextValueChange(this.context)
        columnTextValueChange.title.text = title
        columnTextValueChange.subTitle.text = subTitle
        val resource = if (isIncrease) {
            R.drawable.up_arrow_icon
        } else {
            R.drawable.down_arrow_icon
        }
        columnTextValueChange.ivValueChange.setImageResource(resource)
        return columnTextValueChange
    }

    private fun columnTextValueChangeBorder(
        title: String?,
        subTitle: String?,
        isIncrease: Boolean
    ): ConstraintLayout {
        val columnTextValueChangeBorder = ColumnTextValueChangeBorder(this.context)
        columnTextValueChangeBorder.title.text = title
        columnTextValueChangeBorder.subTitle.text = subTitle
        val textColor = if (isIncrease) {
            Color.parseColor("#319988")
        } else {
            Color.parseColor("#ed5347")
        }
        val background = if (isIncrease) {
            R.drawable.rounded_corner_green
        } else {
            R.drawable.rounded_corner_red
        }
        columnTextValueChangeBorder.subTitle.setTextColor(textColor)
        columnTextValueChangeBorder.title.setTextColor(textColor)
        columnTextValueChangeBorder.title.setBackgroundResource(background)
        return columnTextValueChangeBorder
    }

    private fun rangeItem(minValue: Double, maxValue: Double, value: Double): ConstraintLayout {
        val cl = RangeItem(this.context)
        cl.textLeft.text = convertDouble2Decimal(minValue)
        cl.textRight.text = convertDouble2Decimal(maxValue)
        cl.setRange(maxValue.toInt(), value.toInt())

        return cl
    }

    private fun convertDouble2Decimal(value: Double): String {
        return String.format("%.2f", value)
    }

    fun prettyCount(number: Int): String {
        return if (Math.abs(number / 100000) >= 1)
            (number / 1000).toString() + "K" else {
            number.toString();
        }
    }

    private fun taleRowForTableD(sampleObject: MarketModel): TableRow {
        val taleRowForTableD = TableRow(this.context)

        val loopCount = (tableB!!.getChildAt(0) as TableRow).childCount


        for (x in 0 until loopCount) {
            val params = TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            params.setMargins(0, 0, 0, 1)

            params.width = widthColumn[x]
            when (x) {
                0 -> {
                    val llContent = columnText(
                        sampleObject.nameMarket.name,
                        "Stock",
                        sampleObject.nameMarket.stock
                    )
                    taleRowForTableD.addView(llContent, params)
                }

                1 -> {
                    val llContent = columnTextValueChange(
                        convertDouble2Decimal(sampleObject.last.currentValue),
                        sampleObject.last.id,
                        sampleObject.last.isIncrease
                    )
                    taleRowForTableD.addView(llContent, params)
                }

                2 -> {
                    val clContent = columnTextValueChangeBorder(
                        convertDouble2Decimal(sampleObject.change.currentValue),
                        convertDouble2Decimal(sampleObject.change.percent),
                        sampleObject.change.isIncrease
                    )

                    taleRowForTableD.addView(clContent, params)
                }

                3 -> {
                    val tv = headerTextView(sampleObject.totalVol.toString())
                    tv.gravity = Gravity.CENTER_HORIZONTAL
                    val spanString = SpannableString(prettyCount(sampleObject.totalVol))
                    spanString.setSpan(StyleSpan(Typeface.BOLD), 0, spanString.length, 0)
                    tv.text = spanString
                    taleRowForTableD.addView(tv, params)
                }

                4 -> {
                    val cl = columnTextEndAlignment(
                        sampleObject.bid.value.toString(),
                        prettyCount(sampleObject.totalVol),
                    )
                    taleRowForTableD.addView(cl, params)
                }

                5 -> {
                    val cl = columnTextEndAlignment(
                        sampleObject.ask.value.toString(),
                        prettyCount(sampleObject.totalVol)
                    )
                    taleRowForTableD.addView(cl, params)
                }

                6 -> {
                    val cl = columnTextCenterAlignment(
                        convertDouble2Decimal(sampleObject.openClose.open),
                        convertDouble2Decimal(sampleObject.openClose.close)
                    )
                    taleRowForTableD.addView(cl, params)
                }

                7 -> {
                    val cl = rangeItem(
                        sampleObject.dayRange.min,
                        sampleObject.dayRange.max,
                        sampleObject.dayRange.value
                    )
                    taleRowForTableD.addView(cl, params)

                }

                else -> {
                    val cl = rangeItem(
                        sampleObject.range52Week.min,
                        sampleObject.range52Week.max,
                        sampleObject.range52Week.value
                    )
                    taleRowForTableD.addView(cl, params)
                }

            }
        }

        return taleRowForTableD
    }


    // header standard TextView
    private fun headerTextView(label: String?): TextView {
        val headerTextView = TextView(this.context)
        headerTextView.setBackgroundColor(Color.WHITE)
        headerTextView.text = label
        headerTextView.gravity = Gravity.CENTER
        headerTextView.setPadding(4, 4, 4, 4)

        return headerTextView
    }

    // resizing TableRow height starts here
    private fun resizeHeaderHeight() {
        val productNameHeaderTableRow = tableA!!.getChildAt(0) as TableRow
        val productInfoTableRow = tableB!!.getChildAt(0) as TableRow

        val rowAHeight = this.viewHeight(productNameHeaderTableRow)
        val rowBHeight = this.viewHeight(productInfoTableRow)

        val tableRow =
            if (rowAHeight < rowBHeight) productNameHeaderTableRow else productInfoTableRow
        val finalHeight = if (rowAHeight > rowBHeight) rowAHeight else rowBHeight

        this.matchLayoutHeight(tableRow, finalHeight)
    }

    private val tableRowHeaderCellWidth: Unit
        get() {
            val tableAChildCount = (tableA!!.getChildAt(0) as TableRow).childCount
            val tableBChildCount = (tableB!!.getChildAt(0) as TableRow).childCount



            for (x in 0 until (tableAChildCount + tableBChildCount)) {
                if (x == 0) {
                    headerCellsWidth[x] =
                        this.viewWidth((tableA!!.getChildAt(0) as TableRow).getChildAt(x))
                } else {
                    headerCellsWidth[x] =
                        this.viewWidth((tableB!!.getChildAt(0) as TableRow).getChildAt(x - 1))
                }
            }
        }

    // resize body table row height
    private fun resizeBodyTableRowHeight() {
        val tableC_ChildCount = tableC!!.childCount

        for (x in 0 until tableC_ChildCount) {
            val productNameHeaderTableRow = tableC!!.getChildAt(x) as TableRow
            val productInfoTableRow = tableD!!.getChildAt(x) as TableRow

            val rowAHeight = this.viewHeight(productNameHeaderTableRow)
            val rowBHeight = this.viewHeight(productInfoTableRow)

            val tableRow =
                if (rowAHeight < rowBHeight) productNameHeaderTableRow else productInfoTableRow
            val finalHeight = if (rowAHeight > rowBHeight) rowAHeight else rowBHeight

            this.matchLayoutHeight(tableRow, finalHeight)
        }
    }

    // match all height in a table row
    // to make a standard TableRow height
    private fun matchLayoutHeight(tableRow: TableRow, height: Int) {
        val tableRowChildCount = tableRow.childCount


        // if a TableRow has only 1 child
        if (tableRow.childCount == 1) {
            val view = tableRow.getChildAt(0)
            val params = view.layoutParams as TableRow.LayoutParams
            params.height = height - (params.bottomMargin + params.topMargin)

            return
        }


        // if a TableRow has more than 1 child
        for (x in 0 until tableRowChildCount) {
            val view = tableRow.getChildAt(x)

            val params = view.layoutParams as TableRow.LayoutParams

            if (!isTheHeighestLayout(tableRow, x)) {
                params.height = height - (params.bottomMargin + params.topMargin)
                return
            }
        }
    }

    // check if the view has the highest height in a TableRow
    private fun isTheHeighestLayout(tableRow: TableRow, layoutPosition: Int): Boolean {
        val tableRowChildCount = tableRow.childCount
        var heighestViewPosition = -1
        var viewHeight = 0

        for (x in 0 until tableRowChildCount) {
            val view = tableRow.getChildAt(x)
            val height = this.viewHeight(view)

            if (viewHeight < height) {
                heighestViewPosition = x
                viewHeight = height
            }
        }

        return heighestViewPosition == layoutPosition
    }

    // read a view's height
    private fun viewHeight(view: View): Int {
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        return view.measuredHeight
    }

    // read a view's width
    private fun viewWidth(view: View): Int {
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        return view.measuredWidth
    }

    // horizontal scroll view custom class
    internal inner class MyHorizontalScrollView(context: Context?) :
        HorizontalScrollView(context) {
        override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
            val tag = this.tag as String

            if (tag.equals("horizontal scroll view b", ignoreCase = true)) {
                horizontalScrollViewD!!.scrollTo(l, 0)
            } else {
                horizontalScrollViewB!!.scrollTo(l, 0)
            }
        }
    }

    // scroll view custom class
    internal inner class MyScrollView(context: Context?) : ScrollView(context) {
        override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
            val tag = this.tag as String

            if (tag.equals("scroll view c", ignoreCase = true)) {
                scrollViewD!!.scrollTo(0, t)
            } else {
                scrollViewC!!.scrollTo(0, t)
            }
        }
    }
}