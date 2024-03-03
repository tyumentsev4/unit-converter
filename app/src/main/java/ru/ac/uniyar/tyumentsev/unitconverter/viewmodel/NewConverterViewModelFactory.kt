package ru.ac.uniyar.tyumentsev.unitconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ac.uniyar.tyumentsev.unitconverter.repository.ConverterRepository
import java.lang.IllegalArgumentException

class NewConverterViewModelFactory(private val repository: ConverterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewConverterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewConverterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
