package ru.ac.uniyar.tyumentsev.unitconverter.viewmodel

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
    val converters: LiveData<List<ConverterEntity>>
        get() = _converters
    private val _unitsForSelectedConverter = MutableLiveData<List<UnitEntity>>()
    val unitsForSelectedConverter: LiveData<List<UnitEntity>>
        get() = _unitsForSelectedConverter

    private val _firstNumberUnitPosition = MutableLiveData<Int>()
    val firstNumberUnitPosition: LiveData<Int>
        get() = _firstNumberUnitPosition
    private val _secondNumberUnitPosition = MutableLiveData<Int>()
    val secondNumberUnitPosition: LiveData<Int>
        get() = _secondNumberUnitPosition
    private val _converterPosition = MutableLiveData<Int>()
    val converterPosition: LiveData<Int>
        get() = _converterPosition

    private val _firstNumber = MutableLiveData<Double>()
    val firstNumber: LiveData<Double>
        get() = _firstNumber
    private val _secondNumber = MutableLiveData<Double>()
    val secondNumber: LiveData<Double>
        get() = _secondNumber

    init {
        loadConverters()
    }

    private fun loadConverters() {
        viewModelScope.launch {
            _converters.value = repository.getConverters()
            setConverter(0)
        }
    }

    private fun loadUnitsForConverter() {
        viewModelScope.launch {
            _unitsForSelectedConverter.value = getConverter().let {
                repository.getUnitsForConverter(
                    it.id
                )
            }
        }
    }

    private fun getConverter(): ConverterEntity {
        return converters.value!![converterPosition.value!!]
    }

    fun calculateFirstNumber(number: Double) {
        _firstNumber.value =
            number * getSecondNumberUnit() / getFirstNumberUnit()
    }

    fun calculateSecondNumber(number: Double) {
        _secondNumber.value =
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

    fun setFirstNumberUnit(position: Int, value: Double?) {
        _firstNumberUnitPosition.value = position
        if (value != null) {
            calculateSecondNumber(value)
        }
    }

    fun setSecondNumberUnit(position: Int, value: Double?) {
        _secondNumberUnitPosition.value = position
        if (value != null) {
            calculateSecondNumber(value)
        }
    }

    fun setConverter(position: Int) {
        _converterPosition.value = position
        _firstNumberUnitPosition.value = 0
        _secondNumberUnitPosition.value = 1
        loadUnitsForConverter()
    }
}
