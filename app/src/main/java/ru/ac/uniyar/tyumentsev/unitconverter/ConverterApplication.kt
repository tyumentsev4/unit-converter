package ru.ac.uniyar.tyumentsev.unitconverter

import android.app.Application
import ru.ac.uniyar.tyumentsev.unitconverter.database.AppDatabase
import ru.ac.uniyar.tyumentsev.unitconverter.repository.ConverterRepository

class ConverterApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ConverterRepository(database.converterDao()) }
}
