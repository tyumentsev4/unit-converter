package ru.ac.uniyar.tyumentsev.unitconverter.ui.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ac.uniyar.tyumentsev.unitconverter.models.Converter
import ru.ac.uniyar.tyumentsev.unitconverter.models.Unit

class ConverterViewModel : ViewModel() {
    fun changeConverter(position: Int) {
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
                Unit("mile", 1609.35),
                Unit("yard", 0.9144),
                Unit("foot", 0.3048),
                Unit("inch", 0.0254)
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
                Unit("m^3", 1.0),
                Unit("mL", 1000000.0),
                Unit("L", 1000.0),
                Unit("pt", 2113.376),
                Unit("kw", 1056.688),
                Unit("gal", 1056.688),
                Unit("ft^3", 35.315),
                Unit("in^3", 1023.744),
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
        _firstNumber.value = number * _secondNumberUnit.value!!.value / _firstNumberUnit.value!!.value
    }

    private val _firstNumberUnit = MutableLiveData<Unit>()

    val firstNumberUnit: LiveData<Unit>
        get() = _firstNumberUnit

    fun setFirstNumberUnit(position: Int) {
        _firstNumberUnit.value = _converter.value!!.units[position]
    }

    private val _secondNumber = MutableLiveData<Double>()
    val secondNumber: LiveData<Double>
        get() = _secondNumber

    fun calculateSecondNumber(number: Double) {
        _secondNumber.value = number * _firstNumberUnit.value!!.value / _secondNumberUnit.value!!.value
    }

    private val _secondNumberUnit = MutableLiveData<Unit>()

    val secondNumberUnit: LiveData<Unit>
        get() = _secondNumberUnit

    fun setSecondNumberUnit(position: Int) {
        _secondNumberUnit.value = _converter.value!!.units[position]
    }
}
