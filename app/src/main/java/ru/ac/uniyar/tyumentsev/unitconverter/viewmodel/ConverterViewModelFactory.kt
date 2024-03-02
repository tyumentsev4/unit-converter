package ru.ac.uniyar.tyumentsev.unitconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ac.uniyar.tyumentsev.unitconverter.repository.ConverterRepository
import java.lang.IllegalArgumentException

class ConverterViewModelFactory(private val repository: ConverterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConverterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConverterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
