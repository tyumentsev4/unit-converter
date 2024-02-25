package ru.ac.uniyar.tyumentsev.unitconverter.ui.converter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ac.uniyar.tyumentsev.unitconverter.models.Converter
import ru.ac.uniyar.tyumentsev.unitconverter.models.Unit

class ConverterViewModel : ViewModel() {
    fun changeConverter(position: Int) {
        Log.i("debug", "AAAAAAAAAAAAAAAAAAAAA")
        _converter.value = converters[position]
    }

    fun getUnitNames(): List<String> {
        return _converter.value!!.units.map { it.name }
    }

    val converters = listOf(
        Converter(
            "length",
            listOf(
                Unit("m", 1.0),
                Unit("mm", 0.001),
                Unit("cm", 0.01),
                Unit("dm", 0.1),
                Unit("km", 1000.0),
                Unit("mi", 1609.35),
                Unit("yd", 0.9144),
                Unit("ft", 0.3048),
                Unit("in", 0.0254)
            )
        ),
        Converter(
            "weight",
            listOf(
                Unit("g", 1.0),
                Unit("μg", 0.000001),
                Unit("mg", 0.001),
                Unit("kg", 1000.0),
                Unit("cen", 100000.0),
                Unit("ton", 1000000.0),
                Unit("lb", 453.5922),
                Unit("oz", 28.3495),
            )
        ),
        Converter(
            "area",
            listOf(
                Unit("m³", 1.0),
                Unit("mL", 1000000.0),
                Unit("L", 1000.0),
                Unit("pt", 2113.376),
                Unit("kw", 1056.688),
                Unit("gal", 1056.688),
                Unit("ft³", 35.315),
                Unit("in³", 1023.744),
            )
        ),
    )
    private val _converter = MutableLiveData<Converter>()
    val converter: LiveData<Converter>
        get() = _converter

    private val _firstNumber = MutableLiveData<Double>()
    val firstNumber: LiveData<Double>
        get() = _firstNumber

    fun calculateFirstNumber(number: Double) {
        _firstNumber.value =
            number * getSecondNumberUnit().value / getFirstNumberUnit().value
    }

    fun calculateSecondNumber(number: Double) {
        _secondNumber.value =
            number * getFirstNumberUnit().value / getSecondNumberUnit().value
    }

    private val _firstNumberUnitPosition = MutableLiveData<Int>()
    val firstNumberUnitPosition: LiveData<Int>
        get() = _firstNumberUnitPosition

    fun setFirstNumberUnit(position: Int, value: Double) {
        _firstNumberUnitPosition.value = position
        calculateSecondNumber(value)
    }

    private val _secondNumber = MutableLiveData<Double>()
    val secondNumber: LiveData<Double>
        get() = _secondNumber

    private val _secondNumberUnitPosition = MutableLiveData<Int>()
    val secondNumberUnitPosition: LiveData<Int>
        get() = _secondNumberUnitPosition

    fun setSecondNumberUnit(position: Int, value: Double) {
        _secondNumberUnitPosition.value = position
        calculateSecondNumber(value)
    }

    private fun getFirstNumberUnit(): Unit {
        return converter.value!!.units[_firstNumberUnitPosition.value!!]
    }

    private fun getSecondNumberUnit(): Unit {
        return converter.value!!.units[_secondNumberUnitPosition.value!!]
    }

    init {
        _firstNumber.value = 0.0
        _secondNumber.value = 0.0
        _converter.value = converters[0]
        _firstNumberUnitPosition.value = 0
        _secondNumberUnitPosition.value = 1
    }
}
