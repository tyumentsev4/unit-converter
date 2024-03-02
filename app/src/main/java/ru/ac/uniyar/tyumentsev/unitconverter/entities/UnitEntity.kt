package ru.ac.uniyar.tyumentsev.unitconverter.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = ConverterEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("converterId"),
    onDelete = ForeignKey.CASCADE)])
data class UnitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val value: Double,
    val converterId: Long
)
