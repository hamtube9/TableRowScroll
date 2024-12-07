package com.example.chartexample

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {
    var liveData: MutableLiveData<List<MarketModel>>
    var data: ArrayList<MarketModel>
    var sampleName = arrayOf(
        "Genting Sing",
        "Parkson Retail",
        "Singtel",
        "Mapletree Logust",
        "Marcopolo Market"
    )

    var sampleCode = arrayOf(
        "5E2",
        "21T",
        "SGX",
        "MNX",
        "9NM"
    )

    var sampleMoney = arrayOf(
        "HKD",
        "USD",
        "VND",
        "EUR",
        "RUS"
    )
    var stocks = arrayOf("C", "M", "CD")


    init {
        liveData = MutableLiveData()
        data = ArrayList()
        if (data.isEmpty()){
            for (x in 0..20) {
                data.add(randomData())
            }
        }
        liveData.value = data
        viewModelScope.launch {
            while (true) {
                delay(5000) // Delay for 5 seconds
                updateData()
            }
        }
    }

    private fun updateData() {
        val clone = data.toMutableList()

        for (x in 0..<clone.size) {
            val isChange = Random.nextBoolean()
            if (isChange) {
                val isIncrease = Random.nextBoolean()
                var currentValue: Double
                var percent: Double
                val totalVol = Random.nextInt(9000, 900000)
                if (isIncrease) {
                    currentValue = Random.nextDouble(0.0, 2.76)
                    percent = Random.nextDouble(0.0, 3.0)
                } else {
                    currentValue = Random.nextDouble(-3.0, -0.1)
                    percent = Random.nextDouble(-3.0, -0.5)
                }
                clone[x].last = Last(
                    isIncrease,
                    Random.nextDouble(0.0, 4.37),
                    sampleMoney[(0..4).random()],
                )
                clone[x].change = Change(isIncrease, currentValue, percent)
                clone[x].totalVol = totalVol

                clone[x].openClose = OpenClose(currentValue, currentValue * 1.2)
                clone[x].dayRange = DayRange(currentValue * 0.9, currentValue * 1.2, currentValue)
                clone[x].range52Week =
                    DayRange(currentValue * 0.9, currentValue * 1.2, currentValue)
            }
        }
        liveData.postValue(clone)
    }


    private fun randomData(): MarketModel {
        val isIncrease = Random.nextBoolean()
        var currentValue: Double
        var percent: Double
        val totalVol = Random.nextInt(50000, 200000)
        if (isIncrease) {
            currentValue = Random.nextDouble(0.0, 2.76)
            percent = Random.nextDouble(0.0, 3.0)
        } else {
            currentValue = Random.nextDouble(-3.0, -0.1)
            percent = Random.nextDouble(-3.0, -0.5)

        }
        return MarketModel(
            Code(sampleCode[(0..4).random()], "SGX"),
            NameMarket(
                sampleName[(0..4).random()],
                stocks.toList().subList(0, (0..2).random()).toTypedArray(),
            ),
            Last(
                isIncrease,
                Random.nextDouble(0.0, 4.37),
                sampleMoney[(0..4).random()],
            ),
            Change(isIncrease, currentValue, percent),
            totalVol,
            ComponentVol(totalVol),
            ComponentVol(totalVol),
            OpenClose(currentValue, currentValue * 1.2),
            DayRange(currentValue * 0.9, currentValue * 1.2, currentValue),
            DayRange(currentValue * 0.9, currentValue * 1.2, currentValue),
        )
    }
}