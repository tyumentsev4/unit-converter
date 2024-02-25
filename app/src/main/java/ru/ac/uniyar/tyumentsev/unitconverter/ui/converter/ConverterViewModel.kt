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

    val converters = listOf(
        Converter("Converter 1", listOf(Unit("Unit 1", 1.0), Unit("Unit 2", 2.0))),
        Converter("Converter 2", listOf(Unit("Unit 3", 3.0), Unit("Unit 4", 4.0)))
    )
    private val _converter = MutableLiveData<Converter>()
    val converter: LiveData<Converter>
        get() = _converter
}
