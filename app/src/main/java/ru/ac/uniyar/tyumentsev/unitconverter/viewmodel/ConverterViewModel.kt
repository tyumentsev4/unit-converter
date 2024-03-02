package ru.ac.uniyar.tyumentsev.unitconverter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ac.uniyar.tyumentsev.unitconverter.entities.ConverterEntity
import ru.ac.uniyar.tyumentsev.unitconverter.entities.UnitEntity
import ru.ac.uniyar.tyumentsev.unitconverter.repository.ConverterRepository

class ConverterViewModel(private val repository: ConverterRepository) : ViewModel() {
    private val _converters = MutableLiveData<List<ConverterEntity>>()
    val converters: LiveData<List<ConverterEntity>> = _converters

    // LiveData for the selected converter
    private val _converter = MutableLiveData<ConverterEntity>()
    val converter: LiveData<ConverterEntity> = _converter

    // LiveData for units of the selected converter
    private val _unitsForSelectedConverter = MutableLiveData<List<UnitEntity>>()
    val unitsForSelectedConverter: LiveData<List<UnitEntity>> = _unitsForSelectedConverter

    // LiveData for positions of selected units in the spinner
    val firstNumberUnitPosition = MutableLiveData<Int>()
    val secondNumberUnitPosition = MutableLiveData<Int>()

    // LiveData for conversion results
    val firstNumber = MutableLiveData<Double>()
    val secondNumber = MutableLiveData<Double>()

    init {
        Log.i("aaaa", "sssss")
        loadConverters()
        loadUnitsForConverter()
    }

    private fun loadConverters() {
        Log.i("load", "load converters")
        viewModelScope.launch {
            _converters.value = repository.getConverters()
            _converter.value = converters.value?.get(0)
            Log.i("aaaa", _converters.value.toString())
        }
    }

    fun loadUnitsForConverter() {
        Log.i("load", "load units")
        viewModelScope.launch {
            _unitsForSelectedConverter.value = _converter.value?.let {
                repository.getUnitsForConverter(
                    it.id)
            }
        }
    }

    fun changeConverter(position: Int) {
        Log.i("ccc", "change conertor")
        _converter.value = converters.value?.get(position)
        loadUnitsForConverter()
    }
    fun calculateFirstNumber(number: Double) {
        firstNumber.value =
            number * getSecondNumberUnit() / getFirstNumberUnit()
    }

    fun calculateSecondNumber(number: Double) {
        secondNumber.value =
            number * getFirstNumberUnit() / getSecondNumberUnit()
    }

    private fun getFirstNumberUnit(): Double {
        return _unitsForSelectedConverter.value!![firstNumberUnitPosition.value!!].value
    }

    private fun getSecondNumberUnit(): Double {
        return _unitsForSelectedConverter.value!![secondNumberUnitPosition.value!!].value
    }

    fun getUnitNames(): List<String> {
        return _unitsForSelectedConverter.value?.map { it.name } ?: emptyList()
    }

    fun setFirstNumberUnit(position: Int) {
        firstNumberUnitPosition.value = position
    }

    fun setSecondNumberUnit(position: Int) {
        secondNumberUnitPosition.value = position
    }
}
