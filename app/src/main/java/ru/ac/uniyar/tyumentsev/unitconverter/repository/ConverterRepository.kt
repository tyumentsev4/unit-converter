package ru.ac.uniyar.tyumentsev.unitconverter.repository

import ru.ac.uniyar.tyumentsev.unitconverter.dao.ConverterDao
import ru.ac.uniyar.tyumentsev.unitconverter.entities.ConverterEntity
import ru.ac.uniyar.tyumentsev.unitconverter.entities.UnitEntity

class ConverterRepository(private val converterDao: ConverterDao) {
    suspend fun insertConverter(converter: ConverterEntity) = converterDao.insertConverter(converter)

    suspend fun insertUnit(unit: UnitEntity) = converterDao.insertUnit(unit)

    suspend fun getConverters(): List<ConverterEntity> = converterDao.getConverters()

    suspend fun getUnitsForConverter(converterId: Long): List<UnitEntity> = converterDao.getUnitsForConverter(converterId)
}
