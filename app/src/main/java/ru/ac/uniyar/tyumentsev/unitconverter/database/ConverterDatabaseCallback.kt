package ru.ac.uniyar.tyumentsev.unitconverter.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ac.uniyar.tyumentsev.unitconverter.R
import ru.ac.uniyar.tyumentsev.unitconverter.dao.ConverterDao
import ru.ac.uniyar.tyumentsev.unitconverter.entities.ConverterEntity
import ru.ac.uniyar.tyumentsev.unitconverter.entities.UnitEntity

class ConverterDatabaseCallback(
    private val context: Context
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        populateInitialData(AppDatabase.getDatabase(context).converterDao())
        super.onCreate(db)
    }

    private fun populateInitialData(converterDao: ConverterDao) {
        val converters = listOf(
            ConverterEntity(name = context.resources.getString(R.string.length)),
            ConverterEntity(name = context.resources.getString(R.string.weight)),
            ConverterEntity(name = context.resources.getString(R.string.volume))
        )


        converters.forEach { converter ->
            CoroutineScope(Dispatchers.IO).launch {
                val converterId = converterDao.insertConverter(converter)
                when (converter.name) {
                    context.resources.getString(R.string.length) -> converterDao.insertUnits(
                        listOf(
                            UnitEntity(
                                name = context.resources.getString(R.string.m),
                                value = 1.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.mm),
                                value = 1000.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.cm),
                                value = 100.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.dm),
                                value = 10.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.km),
                                value = 0.001,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.mi),
                                value = 0.000621371,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.yd),
                                value = 1.09361,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.ft),
                                value = 3.28084,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.`in`),
                                value = 39.3701,
                                converterId = converterId
                            )
                        )
                    )

                    context.resources.getString(R.string.weight) -> converterDao.insertUnits(
                        listOf(
                            UnitEntity(
                                name = context.resources.getString(R.string.g),
                                value = 1.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.μg),
                                value = 1000000.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.mg),
                                value = 1000.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.kg),
                                value = 0.001,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.cen),
                                value = 100000.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.ton),
                                value = 1000000.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.lb),
                                value = 0.00220462,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.oz),
                                value = 0.035274,
                                converterId = converterId
                            )
                        )
                    )

                    context.resources.getString(R.string.volume) -> converterDao.insertUnits(
                        listOf(
                            UnitEntity(
                                name = context.resources.getString(R.string.m3),
                                value = 1.0,
                                converterId = converterId
                            ),
                            UnitEntity(
                                name = context.resources.getString(R.string.mL),
                                value = 1000000.0,
                                converterId = converterId
                            ),
                            UnitEntity(
                                name = context.resources.getString(R.string.L),
                                value = 1000.0,
                                converterId = converterId
                            ),
                            UnitEntity(
                                name = context.resources.getString(R.string.pt),
                                value = 2113.376,
                                converterId = converterId
                            ),
                            UnitEntity(
                                name = context.resources.getString(R.string.kw),
                                value = 1056.688,
                                converterId = converterId
                            ),
                            UnitEntity(
                                name = context.resources.getString(R.string.gal),
                                value = 264.172,
                                converterId = converterId
                            ),
                            UnitEntity(
                                name = context.resources.getString(R.string.ft3),
                                value = 35.315,
                                converterId = converterId
                            ),
                            UnitEntity(
                                name = context.resources.getString(R.string.in3),
                                value = 61023.744,
                                converterId = converterId
                            ),
                        )
                    )
                }
            }
        }
    }
}
