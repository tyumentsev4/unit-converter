package ru.ac.uniyar.tyumentsev.unitconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ac.uniyar.tyumentsev.unitconverter.dto.UnitDto
import ru.ac.uniyar.tyumentsev.unitconverter.entities.ConverterEntity
import ru.ac.uniyar.tyumentsev.unitconverter.entities.UnitEntity
import ru.ac.uniyar.tyumentsev.unitconverter.repository.ConverterRepository

class NewConverterViewModel(private val repository: ConverterRepository) : ViewModel() {
    fun createConverter(converterName: String, units: List<UnitDto>) {
        viewModelScope.launch {
            val newConverterId = repository.insertConverter(ConverterEntity(name = converterName))
            units.forEach { unit ->
                repository.insertUnit(
                    UnitEntity(
                        name = unit.name, value = unit.value, converterId = newConverterId
                    )
                )
            }
        }
    }
}
