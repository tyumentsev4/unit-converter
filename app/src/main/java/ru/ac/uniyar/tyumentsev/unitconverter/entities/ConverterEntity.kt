package ru.ac.uniyar.tyumentsev.unitconverter.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConverterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, val name: String
)
