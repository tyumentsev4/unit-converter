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
                                value = 0.001,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.cm),
                                value = 0.01,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.dm),
                                value = 0.1,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.km),
                                value = 1000.0,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.mi),
                                value = 1609.35,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.yd),
                                value = 0.9144,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.ft),
                                value = 0.3048,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.`in`),
                                value = 0.0254,
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
                                value = 0.000001,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.mg),
                                value = 0.001,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.kg),
                                value = 1000.0,
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
                                value = 453.5922,
                                converterId = converterId
                            ), UnitEntity(
                                name = context.resources.getString(R.string.oz),
                                value = 28.3495,
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
                                value = 1056.688,
                                converterId = converterId
                            ),
                            UnitEntity(
                                name = context.resources.getString(R.string.ft3),
                                value = 35.315,
                                converterId = converterId
                            ),
                            UnitEntity(
                                name = context.resources.getString(R.string.in3),
                                value = 1023.744,
                                converterId = converterId
                            ),
                        )
                    )
                }
            }
        }
    }
}
