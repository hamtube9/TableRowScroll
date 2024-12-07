package com.example.chartexample

class MarketModel(
    var code: Code,
    var nameMarket: NameMarket,
    var last: Last,
    var change :Change,
    var totalVol: Int,
    var bid: ComponentVol,
    var ask: ComponentVol,
    var openClose: OpenClose,
    var dayRange: DayRange,
    var range52Week: DayRange
)

class Code(var id: String?, var code: String?)

class NameMarket(var name: String?, var stock: Array<String>, )

class Last(var isIncrease: Boolean, var currentValue: Double, var id: String?, )

class Change(var isIncrease: Boolean, var currentValue: Double, var percent : Double)


class OpenClose(var open: Double, var close: Double, )

class DayRange(var min: Double, var max: Double, var value: Double, )

class ComponentVol(var value : Int, )

