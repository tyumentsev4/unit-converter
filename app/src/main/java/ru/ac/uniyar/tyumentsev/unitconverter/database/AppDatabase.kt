package ru.ac.uniyar.tyumentsev.unitconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.ac.uniyar.tyumentsev.unitconverter.dao.ConverterDao
import ru.ac.uniyar.tyumentsev.unitconverter.entities.ConverterEntity
import ru.ac.uniyar.tyumentsev.unitconverter.entities.UnitEntity

@Database(entities = [ConverterEntity::class, UnitEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun converterDao(): ConverterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "converter_database"
                ).addCallback(ConverterDatabaseCallback(context)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
