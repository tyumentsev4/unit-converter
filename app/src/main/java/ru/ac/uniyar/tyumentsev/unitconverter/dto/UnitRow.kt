package ru.ac.uniyar.tyumentsev.unitconverter.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnitRow(val name: String?, val value: Double?) : Parcelable
