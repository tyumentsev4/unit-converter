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
    val converters: LiveData<List<ConverterEntity>> = _converters

    // LiveData for the selected converter
    val selectedConverter = MutableLiveData<ConverterEntity?>()

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
        loadConverters()
    }

    private fun loadConverters() {
        viewModelScope.launch {
            _converters.value = repository.getConverters()
        }
    }

    fun loadUnitsForConverter(converterId: Long) {
        viewModelScope.launch {
            _unitsForSelectedConverter.value = repository.getUnitsForConverter(converterId)
        }
    }

    fun changeConverter(position: Int) {
        selectedConverter.value = converters.value?.get(position)
        selectedConverter.value?.id?.let { loadUnitsForConverter(it) }
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
        return unitsForSelectedConverter.value!![firstNumberUnitPosition.value!!].value
    }

    private fun getSecondNumberUnit(): Double {
        return unitsForSelectedConverter.value!![secondNumberUnitPosition.value!!].value
    }

    fun getUnitNames(): List<String> {
        return unitsForSelectedConverter.value?.map { it.name } ?: emptyList()
    }

    fun setFirstNumberUnit(position: Int) {
        firstNumberUnitPosition.value = position
    }

    fun setSecondNumberUnit(position: Int) {
        secondNumberUnitPosition.value = position
    }
}
