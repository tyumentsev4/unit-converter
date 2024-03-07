package ru.ac.uniyar.tyumentsev.unitconverter.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.ac.uniyar.tyumentsev.unitconverter.entities.ConverterEntity
import ru.ac.uniyar.tyumentsev.unitconverter.entities.UnitEntity

@Dao
interface ConverterDao {
    @Insert
    suspend fun insertConverter(converter: ConverterEntity): Long

    @Insert
    suspend fun insertUnit(unit: UnitEntity)

    @Insert
    suspend fun insertUnits(units: List<UnitEntity>)

    @Query("SELECT * FROM ConverterEntity")
    suspend fun getConverters(): List<ConverterEntity>

    @Query("SELECT * FROM UnitEntity WHERE converterId = :converterId")
    suspend fun getUnitsForConverter(converterId: Long): List<UnitEntity>

    @Query("SELECT * FROM ConverterEntity WHERE id = :converterId")
    suspend fun getConverter(converterId: Long): ConverterEntity

    @Delete
    suspend fun removeConverter(converter: ConverterEntity)
}
