package ru.ac.uniyar.tyumentsev.unitconverter.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ac.uniyar.tyumentsev.unitconverter.dao.ConverterDao
import ru.ac.uniyar.tyumentsev.unitconverter.entities.ConverterEntity
import ru.ac.uniyar.tyumentsev.unitconverter.entities.UnitEntity

class ConverterDatabaseCallback(
    private val context: Context
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            populateInitialData(AppDatabase.getDatabase(context).converterDao())
        }
    }

    private suspend fun populateInitialData(converterDao: ConverterDao) {
        // Pre-populate data
        val converters = listOf(
            ConverterEntity(name = "length"),
            ConverterEntity(name = "weight"),
            ConverterEntity(name = "volume")
        )

        converters.forEach { converter ->
            val converterId = converterDao.insertConverter(converter)
            when (converter.name) {
                "length" -> listOf(
                    UnitEntity(name = "m", value = 1.0, converterId = converterId),
                    UnitEntity(name ="mm", value = 0.001, converterId = converterId),
                    UnitEntity(name ="cm", value = 0.01, converterId = converterId),
                    UnitEntity(name ="dm", value = 0.1, converterId = converterId),
                    UnitEntity(name ="km", value = 1000.0, converterId = converterId),
                    UnitEntity(name ="mi", value = 1609.35, converterId = converterId),
                    UnitEntity(name ="yd", value = 0.9144, converterId = converterId),
                    UnitEntity(name ="ft", value = 0.3048, converterId = converterId),
                    UnitEntity(name ="in", value = 0.0254, converterId = converterId)
                ).forEach { unit ->
                    converterDao.insertUnit(unit)
                }
                "weight" -> listOf(
                    UnitEntity(name = "g", value = 1.0, converterId = converterId),
                    UnitEntity(name = "μg", value = 0.000001, converterId = converterId),
                    UnitEntity(name = "mg", value = 0.001, converterId = converterId),
                    UnitEntity(name = "kg", value = 1000.0, converterId = converterId),
                    UnitEntity(name = "cen",value =  100000.0, converterId = converterId),
                    UnitEntity(name = "ton",value =  1000000.0, converterId = converterId),
                    UnitEntity(name = "lb", value = 453.5922, converterId = converterId),
                    UnitEntity(name = "oz", value = 28.3495, converterId = converterId),
                ).forEach { unit ->
                    converterDao.insertUnit(unit)
                }
                "volume" -> listOf(
                    UnitEntity(name = "m³", value = 1.0, converterId = converterId),
                    UnitEntity(name = "mL",value = 1000000.0, converterId = converterId),
                    UnitEntity(name = "L", value = 1000.0, converterId = converterId),
                    UnitEntity(name = "pt",value = 2113.376, converterId = converterId),
                    UnitEntity(name = "kw",value = 1056.688, converterId = converterId),
                    UnitEntity(name = "gal",value = 1056.688, converterId = converterId),
                    UnitEntity(name = "ft³", value = 35.315, converterId = converterId),
                    UnitEntity(name = "in³",value = 1023.744, converterId = converterId),
                ).forEach { unit ->
                    converterDao.insertUnit(unit)
                }
            }
        }
    }
}
